package com.xinran.viewslib.qxtabview.recyclerView;

/**
 * Created by qixinh on 16/4/14.
 */
public interface  BaseRefreshHeader {
    public void onMove(float delta) ;
    public boolean releaseAction();
    public void refreshComplate();
    public final static int STATE_NORMAL = 0;
    public final static int STATE_RELEASE_TO_REFRESH = 1;
    public final static int STATE_REFRESHING = 2;
    public final static int STATE_DONE = 3;

}
