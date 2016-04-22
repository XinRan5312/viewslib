package com.xinran.viewslib.qxtabview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by qixinh on 16/4/12.
 */
public abstract class QxBaseScrollView extends ScrollView {
    protected IChangeXiDing changeXiDing;
    protected int reviseHeight = 15;

    public QxBaseScrollView(Context context) {
        super(context);
    }

    public void setReviseHeight(int reviseHeight) {
        this.reviseHeight = reviseHeight;
    }

    public void setChangeXiDing(IChangeXiDing changeXiDing) {
        this.changeXiDing = changeXiDing;
    }

    public QxBaseScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QxBaseScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        changeXiDingTile((LinearLayout) getChildAt(0), l, t, oldl, oldt);
    }

    public abstract void changeXiDingTile(LinearLayout container, int l, int t, int oldl, int oldt);

    public interface IChangeXiDing {
        public abstract void changeXiDingShow(int type);

        public abstract void changeXiDingHide(int type);
    }

}
