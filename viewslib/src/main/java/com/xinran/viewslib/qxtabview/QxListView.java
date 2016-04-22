package com.xinran.viewslib.qxtabview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by qixinh on 16/4/12.
 */
public class QxListView extends ListView{

	public QxListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public QxListView(Context context) {
		super(context);
	}

	public QxListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}


}
