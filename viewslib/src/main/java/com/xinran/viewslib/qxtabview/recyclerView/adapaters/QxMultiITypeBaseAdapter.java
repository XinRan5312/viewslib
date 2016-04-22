package com.xinran.viewslib.qxtabview.recyclerView.adapaters;

import android.content.Context;

import java.util.List;

/**
 * Created by qixinh on 16/4/19.
 */
public abstract class QxMultiITypeBaseAdapter<T> extends QxBaseAdapter<T>{
    public QxMultiITypeBaseAdapter(Context context, List<T> beans) {
        super(context, beans);
    }

    @Override
    public abstract int getItemViewType(int position);
}
