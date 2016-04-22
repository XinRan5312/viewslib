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
        TextView tv = (TextView) container.getChildAt(0);
        TextView tv2 = (TextView) container.getChildAt(2);
        tv2.setText("" + t + ";" + tv2.getTop());

        if (t - tv2.getTop() > reviseHeight) {
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
