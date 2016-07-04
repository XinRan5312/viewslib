package com.xinran.qxviewslib;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by qixinh on 16/4/14.
 */
public class BaseActivity extends FragmentActivity {
    protected Context getContext() {
        return this;
    }

    protected <E extends View> E $(@IdRes int id) {
        return (E) findViewById(id);
    }
}
