package com.blahblah.yandextestapp.utils;

import hugo.weaving.DebugLog;
import rx.Subscriber;

/**
 * Created by Dmitrii Komiakov
 * komiakov.dmitrii@gmail.com
 * komyakovds@byndyusoft.com
 * on 16.04.2017.
 */
public class EmptySubscriber<T> extends Subscriber<T> {

    @DebugLog
    @Override
    public void onCompleted() {

    }

    @DebugLog
    @Override
    public void onError(Throwable e) {

    }

    @DebugLog
    @Override
    public void onNext(T t) {

    }

}
