package com.xinran.qxviewslib;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qixinh on 16/7/7.
 */
public class TestAc extends BaseActivity{
    ListView listView;
    TextView textView;
    private List<String> array = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_listview);
        listView=$(R.id.edit_photo_filter_manage_list_view);
        for(int i=1;i<12;i++){
            array.add(""+i);
        }
        textView=$(R.id.tv_desnity);
        listView.setAdapter(new MyListViewAdapter(this));
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("QX","position:"+position);
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        String str= "Density is " + displayMetrics.density + " densityDpi is " + displayMetrics.densityDpi + " height: " + displayMetrics.heightPixels +
                " width: " + displayMetrics.widthPixels;
        textView.setText(str);
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
