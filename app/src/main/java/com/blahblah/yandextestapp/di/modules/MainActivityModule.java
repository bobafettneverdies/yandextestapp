package com.blahblah.yandextestapp.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.blahblah.yandextestapp.api.ApiProvider;
import com.blahblah.yandextestapp.realm.RealmTranslationRepository;
import com.blahblah.yandextestapp.ui.history.HistoryPresenter;
import com.blahblah.yandextestapp.ui.main.MainActivity;
import com.blahblah.yandextestapp.ui.main.MainRouter;
import com.blahblah.yandextestapp.ui.main.MainRouterImpl;
import com.blahblah.yandextestapp.ui.translation.TranslationPresenter;

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
    MainRouter provideMainRouter(@NonNull Context context) {
        return new MainRouterImpl(((MainActivity) context).getSupportFragmentManager());
    }

    @Provides
    @Singleton
    @NonNull
    TranslationPresenter provideTranslationPresenter(@NonNull ApiProvider apiProvider,
                                                     @NonNull MainRouter mainRouter,
                                                     @NonNull RealmTranslationRepository repository) {
        return new TranslationPresenter(apiProvider, mainRouter.getTranslationFragment(), repository);
    }

    @Provides
    @Singleton
    @NonNull
    HistoryPresenter provideHistoryListPresenter(@NonNull MainRouter mainRouter,
                                                 @NonNull RealmTranslationRepository repository,
                                                 @NonNull TranslationPresenter translationPresenter) {
        return new HistoryPresenter(mainRouter.getHistoryFragment(), repository, translationPresenter);
    }

}
