package com.xinran.qxviewslib;

import android.app.Activity;
import android.app.Application;


import com.xinran.viewslib.qxtabview.strictmode.QxStrictModeManager;

import java.util.ArrayList;

/**
 * Created by qixinh on 16/4/20.
 */
public class QxApp extends Application{
    public static ArrayList<Activity> sLeakyActivities = new ArrayList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();

        QxStrictModeManager.initDefaultStrictMode(this);
    }

}
