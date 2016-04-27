package com.xinran.qxviewslib;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinran.viewslib.qxtabview.QxBaseScrollView;


/**
 * Created by qixinh on 16/4/12.
 */
public class QxScrollView extends QxBaseScrollView {
    public QxScrollView(Context context) {
        super(context);
    }

    public QxScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QxScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void changeXiDingTile(LinearLayout container, int l, int t, int oldl, int oldt) {
        TextView tv = (TextView)container.getChildAt(positionsTitle.get(0));

        if (t - tv.getTop() > reviseHeight) {
            if (changeXiDing != null) {
                changeXiDing.changeXiDingShow(2);
            }
        } else {
            if (changeXiDing != null) {
                changeXiDing.changeXiDingHide(2);
            }
        }
    }

}
