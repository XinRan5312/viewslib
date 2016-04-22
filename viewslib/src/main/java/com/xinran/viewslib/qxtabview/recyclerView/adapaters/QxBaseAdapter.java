package com.xinran.viewslib.qxtabview.recyclerView.adapaters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import com.xinran.viewslib.qxtabview.recyclerView.holders.QxBaseViewHolder;
import com.xinran.viewslib.qxtabview.recyclerView.utils.ViewUtils;

import java.util.List;

/**
 * Created by qixinh on 16/4/19.
 */
public abstract class QxBaseAdapter<T> extends RecyclerView.Adapter<QxBaseViewHolder> implements QxBaseViewHolder.IOnItemClickListener{

    private List<T> mBeans;
    protected Context mContext;
    private boolean mAnimateItems = true;
    private int mLastAnimatedPosition = -1;

    public QxBaseAdapter(Context context, List<T> beans) {
        mContext = context;
        mBeans = beans;
    }

    @Override
    public QxBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        QxBaseViewHolder holder=getViewHolder(LayoutInflater.from(mContext).inflate(getItemLayoutID(viewType), parent, false));
        setIOnItemClickListener(holder);
        return holder;
    }

    protected abstract QxBaseViewHolder getViewHolder(View itemView);

    @Override
    public void onBindViewHolder(QxBaseViewHolder holder, int position) {
        runEnterAnimation(holder.itemView, position);
        onBindDataToView(holder, mBeans.get(position));
    }


    /**
     * 绑定数据到Item的控件中去
     *
     * @param holder
     * @param bean
     */
    protected abstract void onBindDataToView(QxBaseViewHolder holder, T bean);

    /**
     * 取得ItemView的布局文件
     *
     * @return
     */
    public abstract @LayoutRes int getItemLayoutID(int viewType);


    @Override
    public int getItemCount() {
        return mBeans.size();
    }

    public void add(T bean) {
        mBeans.add(bean);
        notifyDataSetChanged();
    }

    public void addAll(List<T> beans) {
        mBeans.addAll(beans);
        notifyDataSetChanged();
    }

    public void clear() {
        mBeans.clear();
        notifyDataSetChanged();
    }

    /***
     * item的加载动画
     *
     * @param view
     * @param position
     */
    private void runEnterAnimation(View view, int position) {
        if (!mAnimateItems) {
            return;
        }
        if (position > mLastAnimatedPosition) {
            mLastAnimatedPosition = position;
            view.setTranslationY(ViewUtils.getScreenHeight(mContext));
            view.animate()
                    .translationY(50)
                    .setStartDelay(100)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(300)
                    .start();
        }
    }
   protected  abstract  void setIOnItemClickListener(QxBaseViewHolder holder);
}
