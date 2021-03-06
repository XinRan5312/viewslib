package com.xinran.viewslib.qxtabview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by qixinh on 16/4/12.
 */
public abstract class QxBaseListViewContainerNoFooter extends LinearLayout {

    public static final int PULL_TO_REFRESH = 2;
    public static final int RELEASE_TO_REFRESH = 3;
    public static final int REFRESHING = 4;
    // pull state
    public static final int PULL_UP_STATE = 0;
    public static final int PULL_DOWN_STATE = 1;
    public static final AtomicBoolean isScroll = new AtomicBoolean(false);
    /**
     * last y
     */
    public int mLastMotionY;

    /**
     * header view
     */
    public View mHeaderView;

    /**
     * list or grid
     */
    public AdapterView<?> mAdapterView;
    /**
     * scrollview
     */
    public ScrollView mScrollView;
    /**
     * header view height
     */
    public int mHeaderViewHeight;

    /**
     * header view image
     */
    public ImageView mHeaderImageView;
    /**
     * header tip text
     */
    public TextView mHeaderTextView;
    /**
     * header refresh time
     */
    public TextView mHeaderUpdateTextView;

    /**
     * header progress bar
     */
    public ProgressBar mHeaderProgressBar;

    /**
     * layout inflater
     */
    public LayoutInflater mInflater;
    /**
     * header view current state
     */
    public int mHeaderState;
    /**
     * footer view current state
     */
    public int mFooterState;
    /**
     * pull state,pull up or pull down;PULL_UP_STATE or PULL_DOWN_STATE
     */
    public int mPullState;
    /**
     * 变为向下的箭头,改变箭头方向
     */
    public RotateAnimation mFlipAnimation;
    /**
     * 变为逆向的箭头,旋转
     */
    public RotateAnimation mReverseFlipAnimation;
    /**
     * footer refresh listener
     */
    public OnHeaderRefreshListener mOnHeaderRefreshListener;

    public QxBaseListViewContainerNoFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public QxBaseListViewContainerNoFooter(Context context) {
        super(context);
        init();
    }

    /**
     * init
     */
    private void init() {
        // 需要设置成vertical
        setOrientation(LinearLayout.VERTICAL);
        mFlipAnimation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(250);
        mFlipAnimation.setFillAfter(true);

        mReverseFlipAnimation = new RotateAnimation(-180, 0,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
        mReverseFlipAnimation.setDuration(250);
        mReverseFlipAnimation.setFillAfter(true);

        mInflater = LayoutInflater.from(getContext());
        // header view 在此添加,保证是第一个添加到linearlayout的最上端
        addHeaderView();
    }

    protected abstract void addHeaderView();

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initContentAdapterView();

    }



    /**
     * init AdapterView like ListView,GridView and so on;or init ScrollView
     */
    protected void initContentAdapterView() {
        int count = getChildCount();
        if (count < 3) {
            throw new IllegalArgumentException(
                    "This layout must contain 3 child views,and AdapterView or ScrollView must in the second position!");
        }
        View view = null;
        for (int i = 0; i < count; ++i) {
            view = getChildAt(i);
            if (view instanceof AdapterView<?>) {
                mAdapterView = (AdapterView<?>) view;
            }
            if (view instanceof ScrollView) {
                // finish later
                mScrollView = (ScrollView) view;
            }
        }
        if (mAdapterView == null && mScrollView == null) {
            throw new IllegalArgumentException(
                    "must contain a AdapterView or ScrollView in this layout!");
        }
    }

    protected void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int y = (int) e.getRawY();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 首先拦截down事件,记录y坐标
                mLastMotionY = y;
                isScroll.set(false);
                break;
            case MotionEvent.ACTION_MOVE:
                // deltaY > 0 是向下运动,< 0是向上运动
                int deltaY = y - mLastMotionY;
                if(deltaY>0){
                    mScrollStateListener.onScrollDown();
                }else{
                    mScrollStateListener.onScrollUp();
                }
                if (isRefreshViewScroll(deltaY)) {
                    isScroll.set(true);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                isScroll.set(false);
            case MotionEvent.ACTION_CANCEL:
                isScroll.set(false);
                break;
        }
        return false;
    }

    /*
     * 如果在onInterceptTouchEvent()方法中没有拦截(即onInterceptTouchEvent()方法中 return
     * false)则由QxBaseListViewContainer 的子View来处理;否则由下面的方法来处理(即由QxBaseListViewContainer自己来处理)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isScroll.set(false);
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mLastMotionY;
                isScroll.set(true);
                if (mPullState == PULL_DOWN_STATE) {// 执行下拉

                    headerPrepareToRefresh(deltaY);
                } else if (mPullState == PULL_UP_STATE) {// 执行上拉


                }
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_UP:
                isScroll.set(false);
            case MotionEvent.ACTION_CANCEL:
                int topMargin = getHeaderTopMargin();
                if (!isScroll.get()) {
                    if (mPullState == PULL_DOWN_STATE) {
                        if (topMargin >= 0) {
                            // 开始刷新
                            headerRefreshing();
                        } else {
                            // 还没有执行刷新，重新隐藏
                            setHeaderTopMargin(-mHeaderViewHeight);
                        }
                    }
                    else if (mPullState == PULL_UP_STATE) {

                    }
                    break;
                }
        }
        return super.onTouchEvent(event);
    }

    public boolean isScrolling(){
        return isScroll.get();
    }
    /**
     * 是否应该到了父View,即QxBaseListViewContainer滑动
     *
     * @param deltaY , deltaY > 0 是向下运动,< 0是向上运动
     * @return
     */
    protected boolean isRefreshViewScroll(int deltaY) {
        if (mHeaderState == REFRESHING || mFooterState == REFRESHING) {
            return true;
        }
        // 对于ListView和GridView
        if (mAdapterView != null) {
            // 子view(ListView or GridView)滑动到最顶端
            if (deltaY > 0) {
//                mScrollStateListener.onScrollDown();
                View child = mAdapterView.getChildAt(0);
                if (child == null) {
                    // 如果mAdapterView中没有数据,不拦截
                    return false;
                }
                if (mAdapterView.getFirstVisiblePosition() == 0
                        && child.getTop() == 0) {
                    mPullState = PULL_DOWN_STATE;

                    return true;
                }
                int top = child.getTop();
                int padding = mAdapterView.getPaddingTop();
                if (mAdapterView.getFirstVisiblePosition() == 0
                        && Math.abs(top - padding) <= 8) {// 这里之前用3可以判断,但现在不行,还没找到原因
                    mPullState = PULL_DOWN_STATE;
                    return true;
                }

            } else if (deltaY < 0) {
//                mScrollStateListener.onScrollUp();
                View lastChild = mAdapterView.getChildAt(mAdapterView
                        .getChildCount() - 1);
                if (lastChild == null) {
                    // 如果mAdapterView中没有数据,不拦截
                    return false;
                }
                // 最后一个子view的Bottom小于父View的高度说明mAdapterView的数据没有填满父view,
                // 等于父View的高度说明mAdapterView已经滑动到最后
                if (lastChild.getBottom() <= getHeight()
                        && mAdapterView.getLastVisiblePosition() == mAdapterView
                        .getCount() - 1) {
                    mPullState = PULL_UP_STATE;
                    return true;
                }
            }
        }
        // 对于ScrollView
        if (mScrollView != null) {
            // 子scroll view滑动到最顶端
            View child = mScrollView.getChildAt(0);
            if (deltaY > 0 && mScrollView.getScrollY() == 0) {
                mPullState = PULL_DOWN_STATE;
//                mScrollStateListener.onScrollDown();
                return true;
            } else if (deltaY < 0
                    && child.getMeasuredHeight() <= getHeight()
                    + mScrollView.getScrollY()) {
                mPullState = PULL_UP_STATE;
//                mScrollStateListener.onScrollUp();
                return true;
            }
        }
        return false;
    }

    /**
     * header 准备刷新,手指移动过程,还没有释放
     *
     * @param deltaY ,手指滑动的距离
     */
    private void headerPrepareToRefresh(int deltaY) {
        int newTopMargin = changingHeaderViewTopMargin(deltaY);
        // 当header view的topMargin>=0时，说明已经完全显示出来了,修改header view 的提示状态
        if (newTopMargin >= 0 && mHeaderState != RELEASE_TO_REFRESH) {
            mHeaderTextView.setText("松开后刷新");
            mHeaderUpdateTextView.setVisibility(View.VISIBLE);
            mHeaderImageView.clearAnimation();
            mHeaderImageView.startAnimation(mFlipAnimation);
            mHeaderState = RELEASE_TO_REFRESH;
        } else if (newTopMargin < 0 && newTopMargin > -mHeaderViewHeight) {// 拖动时没有释放
            mHeaderImageView.clearAnimation();
            mHeaderImageView.startAnimation(mFlipAnimation);
            // mHeaderImageView.
            mHeaderTextView.setText("下拉刷新");
            mHeaderState = PULL_TO_REFRESH;
        }
    }

    /**
     * 修改Header view top margin的值
     *
     * @param deltaY
     */
    private int changingHeaderViewTopMargin(int deltaY) {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        float newTopMargin = params.topMargin + deltaY * 0.3f;
        // 这里对上拉做一下限制,因为当前上拉后然后不释放手指直接下拉,会把下拉刷新给触发了,
        // 表示如果是在上拉后一段距离,然后直接下拉
        if (deltaY > 0 && mPullState == PULL_UP_STATE
                && Math.abs(params.topMargin) <= mHeaderViewHeight) {
            return params.topMargin;
        }
        // 同样地,对下拉做一下限制,避免出现跟上拉操作时一样的bug
        if (deltaY < 0 && mPullState == PULL_DOWN_STATE
                && Math.abs(params.topMargin) >= mHeaderViewHeight) {
            return params.topMargin;
        }
        params.topMargin = (int) newTopMargin;
        mHeaderView.setLayoutParams(params);
        invalidate();
        return params.topMargin;
    }

    /**
     * header refreshing
     */
    private void headerRefreshing() {
        mHeaderState = REFRESHING;
        setHeaderTopMargin(0);
        mHeaderImageView.setVisibility(View.GONE);
        mHeaderImageView.clearAnimation();
        mHeaderImageView.setImageDrawable(null);
        mHeaderProgressBar.setVisibility(View.VISIBLE);
        mHeaderTextView.setText("正在刷新...");
        if (mOnHeaderRefreshListener != null) {
            mOnHeaderRefreshListener.onHeaderRefresh(this);
        }
    }

    /**
     * 设置header view 的topMargin的值
     *
     * @param topMargin ，为0时，说明header view 刚好完全显示出来； 为-mHeaderViewHeight时，说明完全隐藏了
     */
    protected void setHeaderTopMargin(int topMargin) {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        params.topMargin = topMargin;
        mHeaderView.setLayoutParams(params);
        invalidate();
    }

    /**
     * header view 完成更新后恢复初始状态
     */
    public abstract void onHeaderRefreshComplete();

    /**
     * Resets the list to a normal state after a refresh.
     *
     * @param lastUpdated Last updated at.
     */
    public void onHeaderRefreshComplete(CharSequence lastUpdated) {
        setLastUpdated(lastUpdated);
        onHeaderRefreshComplete();
    }

    /**
     * Set a text to represent when the list was last updated.
     *
     * @param lastUpdated Last updated at.
     */
    public void setLastUpdated(CharSequence lastUpdated) {
        if (lastUpdated != null) {
            mHeaderUpdateTextView.setVisibility(View.VISIBLE);
            mHeaderUpdateTextView.setText(lastUpdated);
        } else {
            mHeaderUpdateTextView.setVisibility(View.GONE);
        }
    }

    /**
     * 获取当前header view 的topMargin
     */
    private int getHeaderTopMargin() {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        return params.topMargin;
    }


    /**
     * set headerRefreshListener
     *
     * @param headerRefreshListener
     */
    public void setOnHeaderRefreshListener(
            OnHeaderRefreshListener headerRefreshListener) {
        mOnHeaderRefreshListener = headerRefreshListener;
    }


    /**
     * Interface definition for a callback to be invoked when list/grid header
     * view should be refreshed.
     */
    public interface OnHeaderRefreshListener {
         void onHeaderRefresh(QxBaseListViewContainerNoFooter view);
    }
    public void setScrollStateLinsener(ScrollStateLinsener scrollStateListener){
        this.mScrollStateListener=scrollStateListener;
    }
    private ScrollStateLinsener mScrollStateListener=new ScrollStateLinsener() {

        @Override
        public void onScrollUp() {

        }

        @Override
        public void onScrollDown() {

        }
    };
    public interface ScrollStateLinsener{
        void onScrollUp();
        void onScrollDown();
    }
}
