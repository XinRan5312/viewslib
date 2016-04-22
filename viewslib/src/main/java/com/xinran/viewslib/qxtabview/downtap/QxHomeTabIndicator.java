package com.xinran.viewslib.qxtabview.downtap;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;


/**
 * Created by qixinh on 16/4/15.
 */
public class QxHomeTabIndicator extends TabIndicator{

	public QxHomeTabIndicator(Context context) {
		super(context);
	}

	public QxHomeTabIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	protected LinearLayout.LayoutParams newTabLayoutParam() {
		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(0, -1, 1);
		llp.bottomMargin = (int)BitmapHelper.dip2px(getContext(), 4);
		return llp;
	}

	protected void drawTabLine(Canvas canvas) {
		//don't draw anything
	}


}
