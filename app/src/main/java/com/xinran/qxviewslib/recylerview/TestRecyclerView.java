package com.xinran.qxviewslib.recylerview;

import android.content.Context;

import android.util.AttributeSet;

import com.xinran.viewslib.qxtabview.recyclerView.ProgressStyle;
import com.xinran.viewslib.qxtabview.recyclerView.QxBaseArrowRefreshHeader;
import com.xinran.viewslib.qxtabview.recyclerView.QxBaseLoadingMoreFooter;
import com.xinran.viewslib.qxtabview.recyclerView.QxBaseRecyclerView;



/**
 * Created by qixinh on 16/4/19.
 */
public  class TestRecyclerView extends QxBaseRecyclerView {


    public TestRecyclerView(Context context) {
        this(context, null);
    }

    public TestRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected int getLoadingMoreProgressStyle() {
        return ProgressStyle.SysProgress;
    }

    @Override
    protected QxBaseLoadingMoreFooter initFooterView(Context mContext) {
        return new TestLoadingMoreFooter(mContext) ;
    }

    @Override
    protected int getRefreshProgressStyle() {
        return ProgressStyle.SysProgress;
    }

    @Override
    protected QxBaseArrowRefreshHeader initHeaderView(Context mContext) {
        return new TestArrowRefreshHeader(mContext);
    }


}
