package com.xinran.viewslib.qxtabview.recyclerView.holders;

import android.graphics.Bitmap;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by qixinh on 16/4/19.
 */
public abstract class QxBaseViewHolder extends RecyclerView.ViewHolder {
    private final SparseArray<View> mViews;
    public View itemView;
    private IOnItemClickListener mOnItemClickListener;
    public QxBaseViewHolder(View itemView) {
        super(itemView);
        this.mViews = new SparseArray<>();
        this.itemView = itemView;
        //添加Item的点击事件
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClick(getAdapterPosition());
                }
            }
        });
    }
    public void setOnItemClickListener(IOnItemClickListener itemClickListener){
        this.mOnItemClickListener=itemClickListener;
    }
    public interface IOnItemClickListener{
       public abstract void onItemClick(int position);
    }
    public <T extends View> T getView(@IdRes int viewId) {

        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public void setText(@IdRes int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
    }

    /**
     * 加载drawable中的图片
     *
     * @param viewId
     * @param resId
     */
    public void setImage(@IdRes int viewId, int resId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);

    }
    public void setImage(@IdRes int viewId, Bitmap bitmap) {
        ImageView iv= getView(viewId);
        iv.setImageBitmap(bitmap);

    }
    /**
     * 加载网络上的图片
     *
     * @param viewId
     * @param url
     */
    public abstract void setImageFromInternet(@IdRes int viewId, String url);
}
