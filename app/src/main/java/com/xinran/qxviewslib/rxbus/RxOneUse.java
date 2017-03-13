package com.xinran.qxviewslib.rxbus;

import org.reactivestreams.Subscriber;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by houqixin on 2017/3/13.
 */
public abstract class RxOneUse {
//省去基本的工作
    private CompositeDisposable compositeDisposable=null;
    private Flowable mFlowable;
    public RxOneUse(){
        mFlowable=buildUseCaseObservable();
    }
    protected abstract Flowable buildUseCaseObservable();

    public void execute(Subscriber subscriber) {

        mFlowable.subscribeOn(Schedulers.io())
                         .subscribeOn(AndroidSchedulers.mainThread())
                         .subscribeWith(subscriber);
    }
    public Flowable getFlowable(){
        return mFlowable;
    }
    public void execute(Consumer consumer) {
        if(compositeDisposable==null)compositeDisposable=new CompositeDisposable();
       compositeDisposable.add(mFlowable
               .subscribeOn(Schedulers.io())
               .subscribeOn(AndroidSchedulers.mainThread())
               .subscribe(consumer));
    }
    public void unSubcriber(){
        if(compositeDisposable!=null){
            compositeDisposable.clear();
        }
    }
}
