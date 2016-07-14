package com.xinran.qxviewslib;

import java.io.Serializable;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Created by qixinh on 16/7/12.
 */
public class SerializableWeakReference<E> extends WeakReference<E> implements Serializable {
    public SerializableWeakReference(E r) {
        super(r);
    }

    public SerializableWeakReference(E r, ReferenceQueue<? super E> q) {
        super(r, q);
    }
}
