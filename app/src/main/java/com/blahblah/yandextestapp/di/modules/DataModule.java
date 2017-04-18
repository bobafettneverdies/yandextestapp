package com.blahblah.yandextestapp.di.modules;

import android.support.annotation.NonNull;

import com.blahblah.yandextestapp.realm.RealmTranslationRepository;
import com.blahblah.yandextestapp.realm.RealmTranslationRepositoryImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    @Singleton
    @NonNull
    Gson provideGson() {
        return new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }

    @Provides
    @Singleton
    @NonNull
    RealmTranslationRepository provideRealmTranslationRepository() {
        return new RealmTranslationRepositoryImpl();
    }

}
