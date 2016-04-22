package com.xinran.qxviewslib;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.xinran.qxviewslib.recylerview.TestRecyclerView;
import com.xinran.qxviewslib.recylerview.adapters.UserAdapater;
import com.xinran.qxviewslib.recylerview.beans.User;
import com.xinran.viewslib.qxtabview.recyclerView.ProgressStyle;
import com.xinran.viewslib.qxtabview.recyclerView.QxBaseRecyclerView;
import com.xinran.viewslib.qxtabview.recyclerView.adapaters.QxBaseAdapter;

import java.util.ArrayList;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by qixinh on 16/4/19.
 */
public class RecylerViewMainActivity extends Activity {
    @Bind(R.id.recyclerview)
    TestRecyclerView mRecyclerView;
    private QxBaseAdapter mAdapater;
    private static final int ACTION_INIT = 0x1;
    private static final int ACTION_REFLESH = 0x2;
    private static final int ACTION_LOAD_MORE = 0x3;
    private int mCurrentAction = ACTION_INIT;
    private int mPageSize = 20;
    private int mCurrentPageIndex = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recylerview);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mAdapater=new UserAdapater(this,new ArrayList<User>());
        mRecyclerView.setAdapter(mAdapater);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotatePulse);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.SquareSpin);
        mRecyclerView.setLoadingListener(new QxBaseRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                switchAction(ACTION_REFLESH);
            }

            @Override
            public void onLoadMore() {
                switchAction(ACTION_LOAD_MORE);
            }
        });

        TextView tv_empty = new TextView(this);
        tv_empty.setText("Empty");
        mRecyclerView.setEmptyView(tv_empty);
      switchAction(ACTION_INIT);
    }
    private void switchAction(int action) {
        mCurrentAction = action;
        switch (mCurrentAction) {
            case ACTION_INIT:
                mAdapater.clear();
                mCurrentPageIndex = 1;
                break;
            case ACTION_REFLESH:
                mAdapater.clear();
                mCurrentPageIndex = 1;
                break;
            case ACTION_LOAD_MORE:
                mCurrentPageIndex++;
                break;
        }
        getData();
    }
    ArrayList<User> list;
    private void getData() {
        if(list==null)
        list= new ArrayList<>();
        User user=null;
        for(int i=0;i<15;i++){
            user=new User("jack"+i,i);
            list.add(user);
        }
        mAdapater.addAll(list);
        if (mCurrentAction == ACTION_REFLESH)
            mRecyclerView.refreshComplete();
        if (mCurrentAction == ACTION_LOAD_MORE)
            mRecyclerView.loadMoreComplete();
    }
}
