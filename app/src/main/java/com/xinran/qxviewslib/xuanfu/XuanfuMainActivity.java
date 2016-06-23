package com.xinran.qxviewslib.xuanfu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import com.xinran.qxviewslib.R;
import com.xinran.qxviewslib.xuanfu.view.StickyNavLayout;

import java.util.ArrayList;

public class XuanfuMainActivity extends AppCompatActivity {

    StickyNavLayout stickyNavLayout;
    ListView id_bottomview;
    XuanfuMainAdapter adapter;
    LinearLayout id_topview;
    LinearLayout id_indicatorview;

    ArrayList<String> strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xuanfu_activity_main);

        strings=new ArrayList<>();

        stickyNavLayout= (StickyNavLayout) findViewById(R.id.stickyNavLayout);
        id_bottomview= (ListView) findViewById(R.id.id_bottomview);
        adapter=new XuanfuMainAdapter(this, strings);
        id_bottomview.setAdapter(adapter);
        id_bottomview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(XuanfuMainActivity.this, "click3", Toast.LENGTH_SHORT).show();
            }
        });
        id_topview= (LinearLayout) findViewById(R.id.id_topview);
        id_topview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(XuanfuMainActivity.this, "click", Toast.LENGTH_SHORT).show();
            }
        });
        id_indicatorview= (LinearLayout) findViewById(R.id.id_indicatorview);
        id_indicatorview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(XuanfuMainActivity.this, "click2", Toast.LENGTH_SHORT).show();

                changeItems(7);
            }
        });

        changeItems(4);
    }

    private void changeItems(int num) {
        stickyNavLayout.setContentHeight(num);
        strings.clear();
        for (int i=0;i<num;i++) {
            strings.add("");
        }
        adapter.notifyDataSetChanged();
    }
}
