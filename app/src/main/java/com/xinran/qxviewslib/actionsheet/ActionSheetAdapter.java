package com.xinran.qxviewslib.actionsheet;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xinran.qxviewslib.R;

/**
 * Created by qixinh on 16/6/23.
 */
public class ActionSheetAdapter extends BaseAdapter {

    Context context;
    String title[];

    public ActionSheetAdapter(Context context, String[] title) {
        this.context = context;
        this.title = title;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int position) {
        return title[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.adapter_pop, parent, false);
            holder=new ViewHolder();
            holder.pop_desp= (TextView) convertView.findViewById(R.id.pop_desp);
            convertView.setTag(holder);
        }
        else {
            holder= (ViewHolder) convertView.getTag();
        }
        TypedArray array=context.obtainStyledAttributes(null, R.styleable.SheetParams, R.attr.myThemeStyle, 0);

        holder.pop_desp.setTextColor(array.getColor(R.styleable.SheetParams_textColor, Color.BLACK));
        holder.pop_desp.setTextSize(array.getDimensionPixelSize(R.styleable.SheetParams_textSize, 10));
        holder.pop_desp.setText(title[position]);
        array.recycle();
        return convertView;
    }

    public static class ViewHolder {
        TextView pop_desp;
    }
}
