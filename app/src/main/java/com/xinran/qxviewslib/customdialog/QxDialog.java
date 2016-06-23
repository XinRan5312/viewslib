package com.xinran.qxviewslib.customdialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by qixinh on 16/6/23.
 */
public class QxDialog extends ProgressDialog{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shoProgress(View.INVISIBLE);
    }
//利用反射得到父类的相关私有属性和方法然后改变之
    public void shoProgress(int invisible) {
        try {
            Method setVisibility= TextView.class.getMethod("setVisibility", Integer.TYPE);
            Field mProgressNumber=this.getClass().getSuperclass().getDeclaredField("mProgressNumber");
            mProgressNumber.setAccessible(true);
            TextView number= (TextView) mProgressNumber.get(this);
            setVisibility.invoke(number, invisible);

            Field mProgressPercent=this.getClass().getSuperclass().getDeclaredField("mProgressPercent");
            mProgressPercent.setAccessible(true);
            TextView per= (TextView) mProgressPercent.get(this);
            setVisibility.invoke(per,invisible);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public QxDialog(Context context) {
        super(context);
    }

    public QxDialog(Context context, int theme) {
        super(context, theme);
    }
}
