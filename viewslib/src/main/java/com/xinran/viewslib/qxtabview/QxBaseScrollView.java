package com.xinran.viewslib.qxtabview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

/**
 * Created by qixinh on 16/4/12.
 */
public abstract class QxBaseScrollView extends ScrollView {
    protected IChangeXiDing changeXiDing;
    protected int reviseHeight = 15;
    protected ArrayList<Integer> positionsTitle=new ArrayList<>();
    public QxBaseScrollView(Context context) {
        super(context);
        initPositionsTitle();
    }

    public void setReviseHeight(int reviseHeight) {
        this.reviseHeight = reviseHeight;
    }

    public void setChangeXiDing(IChangeXiDing changeXiDing) {
        this.changeXiDing = changeXiDing;
    }

    public QxBaseScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPositionsTitle();
    }

    public QxBaseScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPositionsTitle();
    }
    private void initPositionsTitle(){
        positionsTitle.add(10);
    }
    public void addPositionsTitle(ArrayList<Integer> positionsOfTitle) {
        if(positionsOfTitle==null||positionsOfTitle.isEmpty())
            throw new NullPointerException("QxBaseScrollView ArrayList<Integer> positionsOfTitle" +
                    "can not be null or empty!");
        this.positionsTitle=positionsOfTitle;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        changeXiDingTile((LinearLayout)getChildAt(0), l, t, oldl, oldt);

        if (mOnAtBottomListener != null && getChildCount() >= 1 && getHeight() + getScrollY() == getChildAt(getChildCount() - 1).getBottom()) {
            mOnAtBottomListener.atBottom();
        }
    }

    public abstract void changeXiDingTile(LinearLayout container, int l, int t, int oldl, int oldt);

    public interface IChangeXiDing {
        public abstract void changeXiDingShow(int type);

        public abstract void changeXiDingHide(int type);
    }

    private OnAtBottomListener mOnAtBottomListener;

    public void setBottomListener(OnAtBottomListener listener) {
        mOnAtBottomListener = listener;
    }

    public interface OnAtBottomListener {
        void atBottom();
    }

}
