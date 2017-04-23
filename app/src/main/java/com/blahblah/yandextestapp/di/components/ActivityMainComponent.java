package com.blahblah.yandextestapp.di.components;

import com.blahblah.yandextestapp.di.modules.ContextModule;
import com.blahblah.yandextestapp.di.modules.DataModule;
import com.blahblah.yandextestapp.di.modules.MainActivityModule;
import com.blahblah.yandextestapp.di.modules.NetworkModule;
import com.blahblah.yandextestapp.ui.history.HistoryListFragment;
import com.blahblah.yandextestapp.ui.main.MainActivity;
import com.blahblah.yandextestapp.ui.translation.TranslationFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by 123 on 10.04.2017.
 */

@Singleton
@Component(modules = {ContextModule.class, DataModule.class, NetworkModule.class, MainActivityModule.class})
public interface ActivityMainComponent {
    void inject(MainActivity mainActivity);

    void inject(TranslationFragment translationFragment);

    void inject(HistoryListFragment historyListFragment);
}
