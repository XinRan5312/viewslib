package com.xinran.viewslib.qxtabview.strictmode;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.NonNull;

/**
 * Created by qixinh on 16/4/20.
 */
public class QxStrictModeManager {
    /**
     * 这个类要写在主APP  module里才生效，现在写这里就是为了练习和记录
     */
    private static void init() {
        //主线程读写文件和网络监视,怕有些方法不兼容所以用的是监视所有detectAll()

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDialog()
                .build());
        //Activity leak和数据库leak 以及IO流的关闭的监视

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .penaltyLog()
                .build());

    }

    public static void initDefaultStrictMode(@NonNull Context context) {
        int appFlags = context.getApplicationInfo().flags;
        if ((appFlags & ApplicationInfo.FLAG_DEBUGGABLE) != 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {

            init();

        }
    }
    //大家可以根据API版本自定义,不用detectAll
    public static void customStrictMode(@NonNull Context context, @NonNull StrictMode.VmPolicy vmPolicy, @NonNull StrictMode.ThreadPolicy threadPolicy) {
        int appFlags = context.getApplicationInfo().flags;
        if ((appFlags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
            StrictMode.setThreadPolicy(threadPolicy);
            StrictMode.setVmPolicy(vmPolicy);
        }
    }

    public static void customStrictModeOnlyVM(@NonNull Context context, @NonNull StrictMode.VmPolicy vmPolicy) {
        int appFlags = context.getApplicationInfo().flags;
        if ((appFlags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
            StrictMode.setVmPolicy(vmPolicy);
        }
    }

    public static void customStrictModeOnlyThread(@NonNull Context context, @NonNull StrictMode.ThreadPolicy threadPolicy) {
        int appFlags = context.getApplicationInfo().flags;
        if ((appFlags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
            StrictMode.setThreadPolicy(threadPolicy);
        }
    }
}
