package com.xinran.qxviewslib;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xinran.viewslib.qxtabview.QxBaseAdapter;
import com.xinran.viewslib.qxtabview.QxBaseListViewContainer;
import com.xinran.viewslib.qxtabview.QxBaseScrollView;
import com.xinran.viewslib.qxtabview.QxListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScrollListviewMainActivity extends AppCompatActivity implements QxListViewContainer.OnHeaderRefreshListener,
        QxListViewContainer.OnFooterRefreshListener,QxScrollView.IChangeXiDing {
    private QxBaseAdapter myAdapter;

    private QxListViewContainer mQxListViewContainer;
    private QxListView listviewOne;
    private QxListView listviewTwo;
    private List<Integer> data;

    private QxBaseScrollView rootview;
    private TextView mTv;
    public  static Context m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_xiding_main);
        mQxListViewContainer = (QxListViewContainer) findViewById(R.id.list_view_root_container);
        listviewOne = (QxListView) findViewById(R.id.list_view_first);
        listviewTwo = (QxListView) findViewById(R.id.list_view_second);
        rootview= (QxScrollView) findViewById(R.id.list_view_top_container);
        rootview.setChangeXiDing(this);
        mTv= (TextView) findViewById(R.id.list_view_tile_top);
        data = new ArrayList<Integer>();
        for (int i = 0; i < 35; i++) {
            data.add(i);
        }
        myAdapter = new QxAdapter(this, data);

        ImageView img=new ImageView(this);
        img.setBackgroundResource(R.drawable.pic2);
        listviewOne.addHeaderView(img);
        listviewOne.setAdapter(myAdapter);

        listviewTwo.setAdapter(myAdapter);
        mQxListViewContainer.setOnHeaderRefreshListener(this);
        mQxListViewContainer.setOnFooterRefreshListener(this);
        mQxListViewContainer.setLastUpdated(new Date().toLocaleString());
        QxApp.sLeakyActivities.add(this);
        m=this;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                Toast.makeText(ScrollListviewMainActivity.this, "加载更多数据!", Toast.LENGTH_SHORT).show();
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

                Toast.makeText(ScrollListviewMainActivity.this, "数据刷新完成!", Toast.LENGTH_SHORT).show();
            }

        }, 3000);
    }
}
