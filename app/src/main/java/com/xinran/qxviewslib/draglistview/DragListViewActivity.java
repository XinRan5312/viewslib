package com.xinran.qxviewslib.draglistview;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xinran.qxviewslib.BaseActivity;
import com.xinran.qxviewslib.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by qixinh on 16/7/6.
 */
public class DragListViewActivity extends BaseActivity {
    private List<String> array = new ArrayList<>();
    private MyListViewAdapter adapter;
    private TextView tvItemContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draglistview);
        init();
    }

    private void init() {
        for(int i=1;i<12;i++){
            array.add(""+i);
        }
        DragListView lv = $(R.id.drag_list_view);
        tvItemContainer=$(R.id.drag_tv_item_container);
        adapter = new MyListViewAdapter(this);
        lv.setAdapter(adapter);
//        lv.setmRemoveMode(DragListView.FLING);//删除每个item的方式，有三种方式
        lv.setDropListener(new DragListView.DropListener() {

            @Override
            public void drop(int from, int to) {
                Collections.swap(array,from,to);
                StringBuilder sb = new StringBuilder();
                for (String str : array) {
                    sb.append(str);
                }
                tvItemContainer.setText(sb.toString());
                adapter.notifyDataSetChanged();
            }
        });
        lv.setRemoveListener(new DragListView.RemoveListener() {


            @Override
            public void remove(int which) {
                  array.remove(which);
                tvItemContainer.setText("remove第"+which+"个元素");
            }
        });
    }

    private class MyListViewAdapter extends BaseAdapter {

        private LayoutInflater li;

        public MyListViewAdapter(Context context) {
            li = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return array.size();
        }

        @Override
        public Object getItem(int position) {
            return array.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = li.inflate(R.layout.drag_list_view_item, null);
            TextView tv = (TextView) view.findViewById(R.id.edit_photo_filter_manage_filtername);
            tv.setGravity(Gravity.CENTER);
            tv.setText(array.get(position));
            return view;
        }

    }
}
