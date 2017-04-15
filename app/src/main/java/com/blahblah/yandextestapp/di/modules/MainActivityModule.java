package com.blahblah.yandextestapp.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.blahblah.yandextestapp.R;
import com.blahblah.yandextestapp.ui.history.HistoryFragment;
import com.blahblah.yandextestapp.ui.main.MainActivity;
import com.blahblah.yandextestapp.ui.main.MainRouter;
import com.blahblah.yandextestapp.ui.main.MainRouterImpl;
import com.blahblah.yandextestapp.ui.translation.TranslationFragment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Dmitrii Komiakov
 * komiakov.dmitrii@gmail.com
 * komyakovds@byndyusoft.com
 * on 15.04.2017.
 */

@Module
public class MainActivityModule {

    @Provides
    @Singleton
    @NonNull
    TranslationFragment provideTranslationFragment() {
        return new TranslationFragment();
    }

    @Provides
    @Singleton
    @NonNull
    HistoryFragment provideHistoryFragment() {
        return new HistoryFragment();
    }

    @Provides
    @Singleton
    @NonNull
    MainRouter provideMainRouter(@NonNull Context context,
                                 @NonNull TranslationFragment translationFragment,
                                 @NonNull HistoryFragment historyFragment) {
        return new MainRouterImpl(((MainActivity) context).getSupportFragmentManager(),
                R.id.main_content, translationFragment, historyFragment);
    }

}
