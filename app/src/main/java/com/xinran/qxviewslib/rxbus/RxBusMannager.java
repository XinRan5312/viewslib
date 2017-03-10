package com.xinran.qxviewslib.rxbus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;

/**
 * Created by houqixin on 2017/3/10.
 */
public class RxBusMannager {
    private static  RxBusMannager sInstance=new RxBusMannager();
    private final RxBus mBroadcast = new RxBus(RxBus.Subjects.PUBLISH);
    private final Map<Class, RxBus> mMapPublishers = new ConcurrentHashMap<>();
    private RxBusMannager() {
    }
    public static RxBusMannager getInstance() {
        return sInstance;
    }

    public <T> Observable<T> register(Class<T> eventType) {
        return mBroadcast.toObservable(eventType);
    }

    public final <T> Observable<T> toPublisher(Class<T> eventType) {
        RxBus rxBus = mMapPublishers.get(eventType);
        if (rxBus == null) {
            rxBus = new RxBus(RxBus.Subjects.BEHAVIOR);
            mMapPublishers.put(eventType, rxBus);
        }
        return rxBus.toObservable(eventType);
    }

    public void post(Object object) {
        mBroadcast.post(object);
    }

    public final void publish(Object object) {
        Class cls = object.getClass();
        RxBus rxBus = mMapPublishers.get(cls);
        if (rxBus == null) {
            rxBus = new RxBus(RxBus.Subjects.BEHAVIOR);
            mMapPublishers.put(cls, rxBus);
        }
        rxBus.post(object);
    }

    public final <T> void cancelPublisher(Class<T> eventType) {
        if (mMapPublishers.containsKey(eventType)) {
            mMapPublishers.remove(eventType);
        }
    }
}
