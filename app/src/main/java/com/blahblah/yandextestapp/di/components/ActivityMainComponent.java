package com.blahblah.yandextestapp.di.components;

import com.blahblah.yandextestapp.di.modules.ContextModule;
import com.blahblah.yandextestapp.di.modules.DataModule;
import com.blahblah.yandextestapp.di.modules.NetworkModule;
import com.blahblah.yandextestapp.ui.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by 123 on 10.04.2017.
 */

@Singleton
@Component(modules = {ContextModule.class, DataModule.class, NetworkModule.class})
public interface ActivityMainComponent {
    void inject(MainActivity mainActivity);
}
