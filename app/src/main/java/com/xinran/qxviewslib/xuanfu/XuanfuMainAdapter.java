package com.xinran.qxviewslib.xuanfu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.xinran.qxviewslib.R;

import java.util.ArrayList;

/**
 * Created by QX on 2016/6/23.
 */
public class XuanfuMainAdapter extends BaseAdapter {

    Context context;
    ArrayList<String> strings;

    public XuanfuMainAdapter(Context context, ArrayList<String> strings) {
        this.context = context;
        this.strings = strings;
    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public Object getItem(int position) {
        return strings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null) {
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.xuanfu_adapter_main, null, false);
            convertView.setTag(holder);
        }
        else {
            holder= (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    private static class ViewHolder {

    }
}
