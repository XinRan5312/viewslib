package com.xinran.viewslib.qxtabview.fragment;

import android.os.Bundle;

/**
 * Created by qixinh on 16/4/15.
 */
public class QxTabFragmentFactory {

    public static QxTapBaseFragment getFragmentWithCls(Class<? extends QxTapBaseFragment> cls) throws InstantiationException, IllegalAccessException {
        return getFragmentWithCls(cls,null);
    }

    public static QxTapBaseFragment getFragmentWithCls(Class<? extends QxTapBaseFragment> cls,Bundle bundle) throws IllegalAccessException, InstantiationException {
        QxTapBaseFragment fragment=null;
        fragment=cls.newInstance();
        if(bundle!=null)
        fragment.setArguments(bundle);
        return fragment;
    }

    public static QxTapBaseFragment getFragmentWithPath(String classPath,Bundle bundle) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        QxTapBaseFragment fragment=null;
        fragment= (QxTapBaseFragment) Class.forName(classPath).newInstance();
        if(bundle!=null)
            fragment.setArguments(bundle);
        return fragment;
    }

    public static QxTapBaseFragment getFragmentWithPath(String classPath) throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return getFragmentWithPath(classPath,null);
    }
}
