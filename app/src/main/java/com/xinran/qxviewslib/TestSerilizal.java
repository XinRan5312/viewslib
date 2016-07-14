package com.xinran.qxviewslib;

import android.content.Context;

/**
 * Created by qixinh on 16/7/12.
 */
public class TestSerilizal {
    private SerializableWeakReference<Context> contextWeakReference;
    public TestSerilizal(Context context){
        contextWeakReference = new SerializableWeakReference<>(context);
    }
}
