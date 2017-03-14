package com.xinran.qxviewslib.okhttp;

import java.util.Map;

/**
 * Created by houqixin on 2017/3/14.
 */
public class OkBaseRequest {
    public String baseUrl;
    public Map<String,String> mapPareams;
    public String jsonPareams;
    private String requestTag;
    public Map<String,String> headers;
    public Class<? extends OKBaseReponse> reponseCls;
    public OkBaseRequest(String tag){
        this.requestTag=tag;
    }
    public RequestMethod mRequestMethod;

    public String getRequestTag() {
        return requestTag;
    }

    public void startRequest(QxOkHttpUtil.HttpCallback callback){

    }
    public void cancelRequest(){

    }
    public enum RequestMethod{
        POST,GET;
    }

}
