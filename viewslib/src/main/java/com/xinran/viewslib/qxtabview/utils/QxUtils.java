package com.xinran.viewslib.qxtabview.utils;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

/**
 * Created by qixinh on 16/4/27.
 */
public class QxUtils {
    private static int getScreenHeight(Activity actvity) {
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        actvity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static void setLinearLayoutParams(LinearLayout linear, Activity actvity) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getScreenHeight(actvity) - 200);

        linear.setLayoutParams(lp);
    }
}
