package com.xinran.qxviewslib.okhttp;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by houqixin on 2017/3/14.
 * 如果不是用OKHttp我们得不到Call不好取消请求就可以在这个中间类QxHttpManager中取消
 */
public class QxHttpManager {
    private QxOkHttpUtil mHttpUtil;
    private ConcurrentMap<String,OkBaseRequest> mMapRequests;
    private static QxHttpManager manager=new QxHttpManager();
    private QxHttpManager(){
        mHttpUtil=new QxOkHttpUtil();
        mMapRequests=new ConcurrentHashMap<>();
    }
    public void excute(OkBaseRequest request, QxOkHttpUtil.HttpCallback callback){
         mHttpUtil.postOne(request, new QxOkHttpUtil.HttpCallback<OKBaseReponse>() {
             @Override
             public void onSuccess(OKBaseReponse data) {

             }
         });
    }
}

