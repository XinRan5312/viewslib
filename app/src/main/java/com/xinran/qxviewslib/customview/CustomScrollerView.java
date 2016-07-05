package com.xinran.qxviewslib.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by qixinh on 16/7/4.
 */
public class CustomScrollerView extends LinearLayout {
    int lastX;
    int currentX;
    int leftBorder;
    int rightBorder;
    private Scroller mScroller;

    public CustomScrollerView(Context context) {
        this(context, null);
    }

    public CustomScrollerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        mScroller = new Scroller(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        leftBorder = getChildAt(0).getLeft();
        rightBorder = getChildAt(getChildCount() - 1).getRight();
        Log.e("WR","leftBorder:"+leftBorder);
        Log.e("WR","rightBorder:"+rightBorder);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getX();
                Log.e("WR","lastX:"+lastX);
                return true;//必须return true不能用 break，要么永远不会走下面的代码，只有return ture才代表接收事件
            case MotionEvent.ACTION_MOVE:
                currentX = (int) event.getX();
                int dex = lastX - currentX;
                Log.e("WR","dex:"+dex);
//                if (getScaleX() + dex <= leftBorder) {
//                    scrollTo(leftBorder, 0);
////                    smoothScrollTo(leftBorder, 0);使用它可以使用插值器产生动画效果
//                    return true;
//                } else if (getScaleX() + dex + getWidth() >= rightBorder) {
//                    scrollTo(rightBorder - getWidth(), 0);
////                    smoothScrollTo(rightBorder - getWidth(), 0);
//                    return true;
//                } else {
////                    smoothScrollBy(dex, 0);
//                    scrollBy(dex, 0);
//                }
                scrollBy(dex, 0);
                lastX = currentX;
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), 0);
            postInvalidate();
        }
        super.computeScroll();
    }

    //调用此方法滚动到目标位置
    //使用它可以使用插值器产生动画效果
    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }

    //调用此方法设置滚动的相对偏移
    //使用它可以使用插值器产生动画效果
    public void smoothScrollBy(int dx, int dy) {

        //设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }
}
