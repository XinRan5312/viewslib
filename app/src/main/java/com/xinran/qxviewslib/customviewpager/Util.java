package com.xinran.qxviewslib.customviewpager;

import android.content.res.Resources;
import android.util.TypedValue;
/**
 * Created by qixinh on 16/7/16.
 */
public class Util {

	public static int dpToPx(Resources res, int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
	}

}
