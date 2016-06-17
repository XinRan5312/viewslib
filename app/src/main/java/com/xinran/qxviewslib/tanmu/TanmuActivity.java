package com.xinran.qxviewslib.tanmu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.xinran.qxviewslib.R;
import com.xinran.qxviewslib.tanmu.barrage.BarrageUtil;

import java.util.Date;

public class TanmuActivity extends AppCompatActivity {
    private BarrageUtil mBarrageUtil;
    private RecyclerView rvBarrage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanmu);
        rvBarrage = (RecyclerView) findViewById(R.id.rvBarrage);
        mBarrageUtil = new BarrageUtil(this, rvBarrage);
    }

    public void onAddClick(View view) {
        mBarrageUtil.addBarrage(new Date().toString(), "聊天消息。。。。", "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=150237755,4294706681&fm=116&gp=0.jpg");
    }
}
