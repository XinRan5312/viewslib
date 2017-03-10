package com.xinran.qxviewslib.rxbus;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by houqixin on 2017/3/10.
 */
public class RxBus {
    private final Subject<Object> bus;

    RxBus(Subjects subjects) {
        switch (subjects) {
            case PUBLISH:
                bus = PublishSubject.create();
                break;
            case BEHAVIOR:
                bus = BehaviorSubject.create();
                break;
            default:
                bus = PublishSubject.create();
        }
    }

    public void post(Object o) {
        bus.onNext(o);
    }

    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

    public enum Subjects {
        PUBLISH(PublishSubject.class),
        BEHAVIOR(BehaviorSubject.class);
        Class cls;

        Subjects(Class cls) {
            this.cls = cls;
        }
    }
}
