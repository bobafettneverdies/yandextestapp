package com.blahblah.yandextestapp.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 123 on 10.04.2017.
 */

@Module
public class ContextModule {

    private final Context context;

    public ContextModule(@NonNull Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    @NonNull
    Context provideContext() {
        return context;
    }

}
