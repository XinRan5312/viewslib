package com.xinran.viewslib.qxtabview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by qixinh on 16/4/12.
 */
public abstract class QxBaseAdapter<T> extends BaseAdapter {
    public static final String TAG = QxBaseAdapter.class.getSimpleName();
    protected List<T> list;
    private Context mContext;

    public QxBaseAdapter(Context context) {

        this.mContext = context;

    }

    public QxBaseAdapter(Context context, @NonNull List<T> data) {
        this.list = data;
        this.mContext = context;

    }

    public void initData(@NonNull List<T> data) {
        if (list == null) {
            list = data;
            notifyDataSetChanged();
        } else {
            list.addAll(data);
        }


    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = createItemView(mContext,position,parent);
        }
        return convertView;
    }

    protected abstract View createItemView(Context mContext,int position,ViewGroup parent);

    public void addData(@NonNull List<T> data) {
        list.addAll(data);
        notifyDataSetChanged();

    }
}
