package com.xinran.viewslib.qxtabview.recyclerView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;


/**
 * Created by qixinh on 16/4/15.
 */
public abstract class QxBaseLoadingMoreFooter extends LinearLayout {


    protected Context mContext;
    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;
	public QxBaseLoadingMoreFooter(Context context) {
		super(context);
		mContext = context;
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public QxBaseLoadingMoreFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}
    public abstract void initView(Context context );

    public abstract void setProgressStyle(int style);

    public abstract void  setState(int state);
}
