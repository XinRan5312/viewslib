package com.xinran.qxviewslib.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.TypeReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by houqixin on 2017/3/14.
 * fastjson使用指南:http://blog.csdn.net/watertekhqx/article/details/62042768
 */
public class QxOkHttpUtil {
    public static String TAG = "debug-okhttp";
    public static boolean isDebug = true;

    private OkHttpClient client;
    // 超时时间
    public static final int TIMEOUT = 1000 * 60;

    //json请求
    public static final MediaType JSON = MediaType
            .parse("application/json; charset=utf-8");

    private Handler handler = new Handler(Looper.getMainLooper());
    private static Map<String, Call> mMapCalls = new HashMap<>();

    public QxOkHttpUtil() {
        // TODO Auto-generated constructor stub
        this.init();
    }

    private void init() {

        client = new OkHttpClient();

        // 设置超时时间
        client.newBuilder().connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS).build();

    }

    /**
     * post请求
     */
    public <T> void postList(final OkBaseRequest req, final HttpCallback<List<T>> callback) {

        Request request = null;
        if (req.mapPareams == null && req.jsonPareams != null) {
            RequestBody body = RequestBody.create(JSON, req.jsonPareams);
            request = new Request.Builder().url(req.baseUrl).post(body).build();
        } else if (!req.mapPareams.isEmpty()) {
            FormBody.Builder builder = new FormBody.Builder();

            /**
             * 遍历key
             */
            if (null != req.mapPareams) {
                for (Map.Entry<String, String> entry : req.mapPareams.entrySet()) {

                    builder.add(entry.getKey(), entry.getValue().toString());

                }
            }

            RequestBody body = builder.build();

            request = new Request.Builder().url(req.baseUrl).post(body).build();
        }
        onStart(callback);
        Call call = client.newCall(request);
        addCallMaps(req.getRequestTag(), call);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException arg1) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(arg1.getMessage());
                    }
                });
                arg1.printStackTrace();
                removeCallMap(req.getRequestTag());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                removeCallMap(req.getRequestTag());
                String resStr = response.body().string();
                final List<T> list = com.alibaba.fastjson.JSON.parseObject(resStr, new TypeReference<List<T>>() {
                });
                if (response.isSuccessful()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(list);
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onError(response.message());
                        }
                    });
                }
            }
        });

    }

    /**
     * post请求
     */
    public <T> void postOne(final OkBaseRequest req, final HttpCallback<T> callback) {
        Request request = null;
        if (req.mapPareams == null && req.jsonPareams != null) {
            RequestBody body = RequestBody.create(JSON, req.jsonPareams);
            request = new Request.Builder().url(req.baseUrl).post(body).build();
        } else if (!req.mapPareams.isEmpty()) {
            FormBody.Builder builder = new FormBody.Builder();

            /**
             * 遍历key
             */
            if (null != req.mapPareams) {
                for (Map.Entry<String, String> entry : req.mapPareams.entrySet()) {

                    builder.add(entry.getKey(), entry.getValue().toString());

                }
            }

            RequestBody body = builder.build();

            request = new Request.Builder().url(req.baseUrl).post(body).build();
        }
        onStart(callback);
        Call call = client.newCall(request);
        addCallMaps(req.getRequestTag(), call);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException arg1) {
                onError(callback, arg1.getMessage());
                arg1.printStackTrace();
                removeCallMap(req.getRequestTag());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                removeCallMap(req.getRequestTag());
                String resStr = response.body().string();
                final T t =com.alibaba.fastjson.JSON.parseObject(resStr, new TypeReference<T>(){});

                if (response.isSuccessful() && t != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null)
                                callback.onSuccess(t);
                        }
                    });
                } else {
                    onError(callback, response.message());
                }
            }
        });

    }


    /**
     * get请求
     */
    public <T> void getList(final OkBaseRequest req, final HttpCallback<List<T>> callback) {
        StringBuilder urlBuilder = new StringBuilder(req.baseUrl);
        if (req.jsonPareams == null && req.mapPareams != null && !req.mapPareams.isEmpty()) {
            urlBuilder.append("?");
            for (Map.Entry<String, String> entry : req.mapPareams.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue());
            }
        } else if (req.jsonPareams.length() > 0) {
            try {
                JSONObject object = new JSONObject(req.jsonPareams);
                Iterator<String> keys = object.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    urlBuilder.append(key).append("=").append(object.get(key));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Request request = new Request.Builder().url(urlBuilder.toString()).build();

        onStart(callback);
        Call call = client.newCall(request);
        addCallMaps(req.getRequestTag(), call);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException arg1) {
                onError(callback, arg1.getMessage());
                arg1.printStackTrace();
                removeCallMap(req.getRequestTag());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                removeCallMap(req.getRequestTag());
                final List<T> list =  com.alibaba.fastjson.JSON.parseObject(response.body().string(), new TypeReference<List<T>>(){});
                //如果愿意也可以根据class如下
                //final List<T> list = (List<T>) com.alibaba.fastjson.JSON.parseArray(response.body().string(), req.reponseCls);
                if (response.isSuccessful() && list != null && list.size() > 0) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null)
                                callback.onSuccess(list);
                        }
                    });
                } else {
                    onError(callback, response.message());
                }
            }

        });

    }

    public <T> void getOne(final OkBaseRequest req, final HttpCallback<T> callback) {
        StringBuilder urlBuilder = new StringBuilder(req.baseUrl);
        if (req.jsonPareams == null && req.mapPareams != null && !req.mapPareams.isEmpty()) {
            urlBuilder.append("?");
            for (Map.Entry<String, String> entry : req.mapPareams.entrySet()) {
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue());
            }
        } else if (req.jsonPareams.length() > 0) {
            try {
                JSONObject object = new JSONObject(req.jsonPareams);
                Iterator<String> keys = object.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    urlBuilder.append(key).append("=").append(object.get(key));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Request request = new Request.Builder().url(urlBuilder.toString()).build();

        onStart(callback);
        Call call = client.newCall(request);
        addCallMaps(req.getRequestTag(), call);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException arg1) {
                onError(callback, arg1.getMessage());
                arg1.printStackTrace();
                removeCallMap(req.getRequestTag());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                removeCallMap(req.getRequestTag());
                final T t =  com.alibaba.fastjson.JSON.parseObject(response.body().string(), new TypeReference<T>(){});
                //如果愿意也可以根据class如下
               // final T t = (T) com.alibaba.fastjson.JSON.parseObject(response.body().string(), req.reponseCls);
                if (response.isSuccessful()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null)
                                callback.onSuccess(t);
                        }
                    });
                } else {
                    onError(callback, response.message());
                }
            }

        });

    }

    /**
     * log信息打印
     *
     * @param params
     */
    public void debug(String params) {
        if (false == isDebug) {
            return;
        }

        if (null == params) {
            Log.d(TAG, "params is null");
        } else {
            Log.d(TAG, params);
        }
    }

    private void onStart(HttpCallback callback) {
        if (null != callback) {
            callback.onStart();
        }
    }

    private void onSuccess(final HttpCallback callback, final String data) {

        debug(data);

        if (null != callback) {
            handler.post(new Runnable() {
                public void run() {
                    // 需要在主线程的操作。
                    callback.onSuccess(data);
                }
            });
        }
    }

    private void onError(final HttpCallback callback, final String msg) {
        if (null != callback) {
            handler.post(new Runnable() {
                public void run() {
                    // 需要在主线程的操作。
                    callback.onError(msg);
                }
            });
        }
    }

    /**
     * http请求回调
     *
     * @author Flyjun
     */
    public static abstract class HttpCallback<T> {
        // 开始
        public void onStart() {
        }

        // 成功回调
        public abstract void onSuccess(T data);

        // 失败回调
        public void onError(String msg) {
        }

    }

    public void removeCallMap(String tag) {
        if (mMapCalls.containsKey(tag)) {
            if (!(mMapCalls.get(tag).isCanceled())) {
                mMapCalls.get(tag).cancel();
            }
            mMapCalls.remove(tag);
        }
    }

    private void addCallMaps(String tag, Call call) {
        if (mMapCalls.containsKey(tag)) {
            if (!(mMapCalls.get(tag).isCanceled())) {
                mMapCalls.get(tag).cancel();
            }
            mMapCalls.remove(tag);
        } else {
            mMapCalls.put(tag, call);
        }
    }

}
