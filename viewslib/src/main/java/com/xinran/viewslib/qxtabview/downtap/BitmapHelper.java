package com.xinran.viewslib.qxtabview.downtap;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by qixinh on 16/4/16.
 */
public class BitmapHelper {
    public static   int dip2px(Context context, float dp) {
        return (int) (convertUnitToPixel(context, TypedValue.COMPLEX_UNIT_DIP, dp) + 0.5f);
    }
    private static float convertUnitToPixel(Context context, int unit, float in) {
        return TypedValue.applyDimension(unit, in, context.getResources().getDisplayMetrics());
    }
    public static float dip2pxF(Context context, float dp) {
        return convertUnitToPixel(context, TypedValue.COMPLEX_UNIT_DIP, dp);
    }
}
