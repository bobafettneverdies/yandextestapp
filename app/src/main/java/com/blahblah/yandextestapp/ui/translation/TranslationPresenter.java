package com.blahblah.yandextestapp.ui.translation;

import com.blahblah.yandextestapp.api.ApiProvider;

/**
 * Created by Dmitrii Komiakov
 * komiakov.dmitrii@gmail.com
 * komyakovds@byndyusoft.com
 * on 16.04.2017.
 */
public class TranslationPresenter {

    private final ApiProvider apiProvider;
    private final TranslationView translationView;

    public TranslationPresenter(ApiProvider apiProvider, TranslationView translationView) {
        this.apiProvider = apiProvider;
        this.translationView = translationView;
    }
}
