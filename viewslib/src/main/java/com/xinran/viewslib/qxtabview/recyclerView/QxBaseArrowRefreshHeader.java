package com.xinran.viewslib.qxtabview.recyclerView;

import android.animation.ValueAnimator;
import android.content.Context;

import android.os.Handler;
import android.util.AttributeSet;

import android.view.animation.Animation;

import android.widget.LinearLayout;


/**
 * Created by qixinh on 16/4/11.
 */
public abstract class QxBaseArrowRefreshHeader extends LinearLayout implements BaseRefreshHeader {
    protected int mState = STATE_NORMAL;
    protected Animation mRotateUpAnim;
    protected Animation mRotateDownAnim;
    protected final int ROTATE_ANIM_DURATION = 180;
    public int mMeasuredHeight;
    protected Context mContext;
    protected static long lastRefleshTime = 0;//上次刷新时间应该写入本地,在这里做还是有点不当

    public QxBaseArrowRefreshHeader(Context context) {
        super(context);
        mContext = context;
        initView(context);

    }

    /**
     * @param context
     * @param attrs
     */
    public QxBaseArrowRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    protected void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(getVisiableHeight(), destHeight);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setVisiableHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    public String friendlyTime(boolean isRecorde) {
        if (lastRefleshTime == 0) {
            if (isRecorde)
                lastRefleshTime = System.currentTimeMillis();
            return "";
        }
        //获取time距离当前的秒数
        long currentTime = System.currentTimeMillis();
        int ct = (int) ((currentTime - lastRefleshTime) / 1000);
        if (isRecorde)
            lastRefleshTime = currentTime;
        if (ct == 0) {
            return "刚刚";
        }

        if (ct > 0 && ct < 60) {
            return ct + "秒前";
        }

        if (ct >= 60 && ct < 3600) {
            return Math.max(ct / 60, 1) + "分钟前";
        }
        if (ct >= 3600 && ct < 86400)
            return ct / 3600 + "小时前";
        if (ct >= 86400 && ct < 2592000) { //86400 * 30
            int day = ct / 86400;
            return day + "天前";
        }
        if (ct >= 2592000 && ct < 31104000) { //86400 * 30
            return ct / 2592000 + "月前";
        }
        return ct / 31104000 + "年前";
    }

    public void reset() {
        smoothScrollTo(0);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                setState(STATE_NORMAL);
            }
        }, 500);
    }

    @Override
    public boolean releaseAction() {
        boolean isOnRefresh = false;
        int height = getVisiableHeight();
        if (height == 0) // not visible.
            isOnRefresh = false;

        if (getVisiableHeight() > mMeasuredHeight && mState < STATE_REFRESHING) {
            setState(STATE_REFRESHING);
            isOnRefresh = true;
        }
        // refreshing and header isn't shown fully. do nothing.
        if (mState == STATE_REFRESHING && height <= mMeasuredHeight) {
            //return;
        }
        int destHeight = 0; // default: scroll back to dismiss header.
        // is refreshing, just scroll back to show all the header.
        if (mState == STATE_REFRESHING) {
            destHeight = mMeasuredHeight;
        }
        smoothScrollTo(destHeight);

        return isOnRefresh;
    }

    @Override
    public void onMove(float delta) {
        if (getVisiableHeight() > 0 || delta > 0) {
            setVisiableHeight((int) delta + getVisiableHeight());
            if (mState <= STATE_RELEASE_TO_REFRESH) { // 未处于刷新状态，更新箭头
                if (getVisiableHeight() > mMeasuredHeight) {
                    setState(STATE_RELEASE_TO_REFRESH);
                } else {
                    setState(STATE_NORMAL);
                }
            }
        }
    }

    public int getState() {
        return mState;
    }

    public abstract void setArrowImageView(int resid);

    public abstract void setProgressStyle(int style);

    public abstract void setState(int state);

    public abstract void onReadyToReflesh();

    public abstract void setVisiableHeight(int height);

    public abstract int getVisiableHeight();

    public abstract void initView(Context context);
}
