package com.xinran.qxviewslib.recylerview.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.xinran.qxviewslib.R;
import com.xinran.qxviewslib.recylerview.beans.User;
import com.xinran.qxviewslib.recylerview.holders.UserHolder;
import com.xinran.viewslib.qxtabview.recyclerView.adapaters.QxBaseAdapter;
import com.xinran.viewslib.qxtabview.recyclerView.holders.QxBaseViewHolder;

import java.util.List;

/**
 * Created by qixinh on 16/4/19.
 */
public class UserAdapater extends QxBaseAdapter<User> {

    public UserAdapater(Context context, List<User> beans) {
        super(context, beans);
    }

    @Override
    protected QxBaseViewHolder getViewHolder(View itemView) {

        return new UserHolder(itemView);
    }

    @Override
    protected void onBindDataToView(QxBaseViewHolder holder, User bean) {
             holder.setText(R.id.wrecyler_textview_item,bean.name);
    }

    @Override
    public int getItemLayoutID(int viewType) {
        return R.layout.recylerview_item_view;
    }

    /**
     * 下面两个方法如果如要就实现如果不需要就空实现
     * @param holder
     */
    @Override
    protected void setIOnItemClickListener(QxBaseViewHolder holder) {

        holder.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(mContext,"item"+position,Toast.LENGTH_LONG).show();
    }
}
