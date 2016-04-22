package com.xinran.qxviewslib;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.xinran.viewslib.qxtabview.QxBaseAdapter;

import java.util.List;

/**
 * Created by qixinh on 16/4/12.
 */
public class QxAdapter extends QxBaseAdapter<Integer> {

    public QxAdapter(Context context) {
        super(context);
    }

    public QxAdapter(Context context, List<Integer> data) {
        super(context, data);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    protected View createItemView(Context mContext, int position, ViewGroup parent) {
        TextView tv = new TextView(mContext);
        Integer t = list.get(position);
        tv.setText("" + t);
        return tv;
    }


}
