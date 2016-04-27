package com.xinran.qxviewslib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xinran.viewslib.qxtabview.QxBaseListViewContainerNoFooter;


/**
 * Created by qixinh on 16/4/12.
 */
public class QxListViewContainerNoFooter extends QxBaseListViewContainerNoFooter {


    public QxListViewContainerNoFooter(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public QxListViewContainerNoFooter(Context context) {
        super(context);

    }

    @Override
    protected void addHeaderView() {
        // header view
        mHeaderView = mInflater.inflate(R.layout.refresh_header, this, false);

        mHeaderImageView = (ImageView) mHeaderView
                .findViewById(R.id.pull_to_refresh_image);
        mHeaderTextView = (TextView) mHeaderView
                .findViewById(R.id.pull_to_refresh_text);
        mHeaderUpdateTextView = (TextView) mHeaderView
                .findViewById(R.id.pull_to_refresh_updated_at);
        mHeaderProgressBar = (ProgressBar) mHeaderView
                .findViewById(R.id.pull_to_refresh_progress);
        // header layout
        measureView(mHeaderView);

        mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                mHeaderViewHeight);
        // 设置topMargin的值为负的header View高度,即将其隐藏在最上方
        params.topMargin = -(mHeaderViewHeight);
        addView(mHeaderView, params);

    }


    @Override
    public void onHeaderRefreshComplete() {
        setHeaderTopMargin(-mHeaderViewHeight);
        mHeaderImageView.setVisibility(View.VISIBLE);
        mHeaderImageView.setImageResource(R.drawable.ic_pulltorefresh_arrow);
        mHeaderTextView.setText("下拉刷新");
        mHeaderProgressBar.setVisibility(View.GONE);
        mHeaderState = PULL_TO_REFRESH;
    }



}
