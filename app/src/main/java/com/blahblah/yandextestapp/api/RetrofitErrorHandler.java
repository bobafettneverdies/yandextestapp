package com.blahblah.yandextestapp.api;

import javax.inject.Inject;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;

/**
 * Created by Dmitrii Komiakov
 * komiakov.dmitrii@gmail.com
 * komyakovds@byndyusoft.com
 * on 14.04.2017.
 */
public class RetrofitErrorHandler<T> implements Observable.Transformer<T, T> {

    @Inject
    public RetrofitErrorHandler() { }

    @SuppressWarnings("unchecked")
    public <R> Observable.Transformer<R, R> cast() {
        return (Observable.Transformer<R, R>) this;
    }

    @Override
    public Observable<T> call(Observable<T> upstream) {
        return upstream
                .onErrorResumeNext(throwable -> {
                    if (throwable instanceof HttpException) {
                        HttpException error = (HttpException) throwable;
                        Observable.error(error);
                    }
                    return Observable.error(throwable);
                });
    }

}
