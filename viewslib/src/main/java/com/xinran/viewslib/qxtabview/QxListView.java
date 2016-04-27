package com.xinran.viewslib.qxtabview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by qixinh on 16/4/12.
 */
public class QxListView extends ListView {
    private int mLastY;
    private int mLastX;
    private  int slopVelocity=200;
    private  int slopAway=100;
    private boolean isAtBottom;
    private boolean isScrolling = false;
    private VelocityTracker velocity;

    public QxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnScrollStateLisener();
    }

    private void setOnScrollStateLisener() {
        this.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case OnScrollListener.SCROLL_STATE_FLING:
                        isScrolling = false;
                        break;
                    case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        isScrolling = true;
                        break;
                    case OnScrollListener.SCROLL_STATE_IDLE:
                        isScrolling = false;
                        if (view.getLastVisiblePosition() == (view.getCount() - 1)) {
                            if (mOnLoadMoreListener != null) {
                                mOnLoadMoreListener.prepareToLoadMore();
                                mOnLoadMoreListener.onLoadMoreData();
                            }
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    public boolean isScrolling() {
        return isScrolling;
    }

    public QxListView(Context context) {
        super(context);
        setOnScrollStateLisener();
    }

    public QxListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnScrollStateLisener();
    }

    public void setIsAtBottom(boolean flag) {
        isAtBottom = flag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        int y = (int) ev.getRawY();
        int x = (int) ev.getRawX();
        if (velocity == null) {
            velocity = VelocityTracker.obtain();
        }
        velocity.addMovement(ev);
        velocity.computeCurrentVelocity(1000);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                mLastX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mLastY;
                int deltaX = x - mLastX;
                int velocityX = (int) velocity.getXVelocity();
                int velocityY = (int) velocity.getYVelocity();
                if (Math.abs(velocityX) > slopVelocity&&Math.abs(deltaX)>slopAway) {
                    isAtBottom=false;
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                if (Math.abs(velocityY) > slopVelocity&&Math.abs(deltaY)>slopAway) {
                    isAtBottom=true;
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                if (deltaY > 0) {
                    View child = getChildAt(0);
                    if (child != null) {


                        if (getFirstVisiblePosition() == 0
                                && child.getTop() == 0) {
                            isAtBottom = false;
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }

                        int top = child.getTop();
                        int padding = getPaddingTop();
                        if (getFirstVisiblePosition() == 0
                                && Math.abs(top - padding) <= 8) {//这里之前用3可以判断,但现在不行,还没找到原因
                            isAtBottom = false;
                            getParent().requestDisallowInterceptTouchEvent(false);

                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        return super.onTouchEvent(ev);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (isAtBottom) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        return super.onInterceptTouchEvent(ev);
    }

    public void setSlopAway(int slopAway) {
        this.slopAway = slopAway;
    }

    public void setSlopVelocity(int slopVelocity) {
        this.slopVelocity = slopVelocity;
    }
//	@Override
//	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//
//		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//				MeasureSpec.AT_MOST);
//		super.onMeasure(widthMeasureSpec, expandSpec);
//	}

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    private OnLoadMoreListener mOnLoadMoreListener;

    public interface OnLoadMoreListener {
        void prepareToLoadMore();

        void onLoadMoreData();

        void onLoadMoreDataComplete();
    }
}
