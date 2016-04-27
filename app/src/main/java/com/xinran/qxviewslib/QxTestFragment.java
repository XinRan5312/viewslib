package com.xinran.qxviewslib;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xinran.viewslib.qxtabview.QxBaseAdapter;
import com.xinran.viewslib.qxtabview.QxBaseListViewContainerNoFooter;
import com.xinran.viewslib.qxtabview.QxBaseScrollView;
import com.xinran.viewslib.qxtabview.QxListView;
import com.xinran.viewslib.qxtabview.fragment.QxTapBaseFragment;
import com.xinran.viewslib.qxtabview.utils.QxUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by qixinh on 16/4/15.
 */
public class QxTestFragment extends QxTapBaseFragment implements QxBaseListViewContainerNoFooter.OnHeaderRefreshListener,
        QxScrollView.IChangeXiDing, QxBaseListViewContainerNoFooter.ScrollStateLinsener, QxBaseScrollView.OnAtBottomListener, QxListView.OnLoadMoreListener {
    private QxBaseAdapter myAdapter;

    private QxBaseListViewContainerNoFooter mQxListViewContainer;
    private QxListView listviewOne;
    private List<Integer> data;

    private QxBaseScrollView rootview;
    private TextView mTv;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_scroll_xidingnofooter_main, container, false);
    }

    @Override
    public void dealView(View view) {

        LinearLayout listviewContainer = findView(R.id.container_listview);
        LinearLayout cardContainer=findView(R.id.card_container);
        QxUtils.setLinearLayoutParams(listviewContainer, getActivity());
        mQxListViewContainer = findView(R.id.list_view_root_container);
        listviewOne = findView(R.id.list_view_first);
        rootview = findView(R.id.list_view_top_container);
        rootview.setBottomListener(this);
        rootview.setChangeXiDing(this);
        mTv = findView(R.id.list_view_tile_top);
        data = new ArrayList<>();
        TextView tv=null;
        for (int i = 0; i < 10; i++) {
            tv=new TextView(this.getActivity());
            tv.setText("Card=="+i);
            cardContainer.addView(tv,i);

        }
        for (int i = 0; i < 50; i++) {
            data.add(i);
        }
        myAdapter = new QxAdapter(getActivity(), data);

//        ImageView img = new ImageView(getActivity());
//        img.setBackgroundResource(R.drawable.pic2);
//        listviewOne.addHeaderView(img);
        listviewOne.setAdapter(myAdapter);

        mQxListViewContainer.setOnHeaderRefreshListener(this);
        mQxListViewContainer.setScrollStateLinsener(this);
        mQxListViewContainer.setLastUpdated(new Date().toLocaleString());

        listviewOne.setOnLoadMoreListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        rootview.post(new Runnable() {
            @Override
            public void run() {
                rootview.scrollTo(0,0);
            }
        });
    }

    @Override
    public void changeXiDingShow(int type) {
        if (type == 2) {
            mTv.setText("我是listView Title");
        }
    }

    @Override
    public void changeXiDingHide(int type) {
        if (type == 2) {
            mTv.setText("");
        }
    }


    @Override
    public void onHeaderRefresh(QxBaseListViewContainerNoFooter view) {
        mQxListViewContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                mQxListViewContainer.onHeaderRefreshComplete("更新于:"
                        + Calendar.getInstance().getTime().toLocaleString());
                mQxListViewContainer.onHeaderRefreshComplete();

                Toast.makeText(getActivity(), "数据刷新完成!", Toast.LENGTH_SHORT).show();
            }

        }, 3000);
    }

    @Override
    public void onScrollUp() {

//        mTv.setText("onScrollUp");
    }

    @Override
    public void onScrollDown() {
//        mTv.setText("onScrollDown");
    }

    @Override
    public void atBottom() {
        listviewOne.setIsAtBottom(true);
    }

    @Override
    public void prepareToLoadMore() {
        mTv.setText("prepareToLoadMore()");
    }

    @Override
    public void onLoadMoreData() {
        mTv.setText("onLoadMoreData()...");
        mQxListViewContainer.postDelayed(new Runnable() {

            @Override
            public void run() {
                myAdapter.addData(data);
                onLoadMoreDataComplete();
            }

        }, 3000);
    }

    @Override
    public void onLoadMoreDataComplete() {
        mTv.setText("onLoadMoreDataComplete()");
    }
}
