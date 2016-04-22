package com.xinran.qxviewslib;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xinran.viewslib.qxtabview.QxBaseAdapter;
import com.xinran.viewslib.qxtabview.QxBaseListViewContainer;
import com.xinran.viewslib.qxtabview.QxBaseScrollView;
import com.xinran.viewslib.qxtabview.QxListView;
import com.xinran.viewslib.qxtabview.fragment.QxTapBaseFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by qixinh on 16/4/15.
 */
public class QxTestFragment extends QxTapBaseFragment implements QxListViewContainer.OnHeaderRefreshListener,
        QxListViewContainer.OnFooterRefreshListener,QxScrollView.IChangeXiDing {
    private QxBaseAdapter myAdapter;

    private QxListViewContainer mQxListViewContainer;
    private QxListView listviewOne;
    private QxListView listviewTwo;
    private List<Integer> data;

    private QxBaseScrollView rootview;
    private TextView mTv;
    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_scroll_xiding_main,container,false);
    }

    @Override
    public void dealView(View view) {
        mQxListViewContainer = findView(R.id.list_view_root_container);
        listviewOne = findView(R.id.list_view_first);
        listviewTwo = findView(R.id.list_view_second);
        rootview= findView(R.id.list_view_top_container);
        rootview.setChangeXiDing(this);
        mTv= findView(R.id.list_view_tile_top);
        data = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++) {
            data.add(i);
        }
        myAdapter = new QxAdapter(getActivity(), data);

        ImageView img=new ImageView(getActivity());
        img.setBackgroundResource(R.drawable.pic2);
        listviewOne.addHeaderView(img);
        listviewOne.setAdapter(myAdapter);

        listviewTwo.setAdapter(myAdapter);
        mQxListViewContainer.setOnHeaderRefreshListener(this);
        mQxListViewContainer.setOnFooterRefreshListener(this);
        mQxListViewContainer.setLastUpdated(new Date().toLocaleString());
    }

    @Override
    public void changeXiDingShow(int type) {
        if(type==2){
            mTv.setText("我是第二个listView");
        }
    }

    @Override
    public void changeXiDingHide(int type) {
        if(type==2){
            mTv.setText("我是第一个listView");
        }
    }

    @Override
    public void onFooterRefresh(QxBaseListViewContainer view) {
        mQxListViewContainer.postDelayed(new Runnable() {

            @Override
            public void run() {
                mQxListViewContainer.onFooterRefreshComplete();
                myAdapter.addData(data);
                Toast.makeText(getActivity(), "加载更多数据!", Toast.LENGTH_SHORT).show();
            }

        }, 3000);
    }

    @Override
    public void onHeaderRefresh(QxBaseListViewContainer view) {
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
}
