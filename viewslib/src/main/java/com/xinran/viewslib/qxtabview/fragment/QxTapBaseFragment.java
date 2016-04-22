package com.xinran.viewslib.qxtabview.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by qixinh on 16/4/12.
 */
public abstract class QxTapBaseFragment extends Fragment {
    private View mContentView;
    private Context mContext;
    public  static final String TAG=QxTapBaseFragment.class.getSimpleName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView=createView(inflater,container,savedInstanceState);
        mContext=getContext();
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dealView(view);
    }
    protected <T extends View> T findView(@IdRes int id){
        return (T) mContentView.findViewById(id);
    }
    protected View getContentView() {
        return mContentView;
    }

    public Context getViewContext() {
        return mContext;
    }
    public abstract View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    public abstract void dealView(View view);

}
