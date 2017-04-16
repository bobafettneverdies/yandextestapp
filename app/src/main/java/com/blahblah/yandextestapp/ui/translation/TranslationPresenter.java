package com.blahblah.yandextestapp.ui.translation;

import com.blahblah.yandextestapp.api.ApiProvider;
import com.blahblah.yandextestapp.domain.language.LanguageHub;
import com.blahblah.yandextestapp.utils.EmptySubscriber;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Dmitrii Komiakov
 * komiakov.dmitrii@gmail.com
 * komyakovds@byndyusoft.com
 * on 16.04.2017.
 */
public class TranslationPresenter {

    private LanguageHub languageHub;
    private String srcLanguage;
    private String dstLanguage;

    private final ApiProvider apiProvider;
    private final TranslationView translationView;

    @Inject
    public TranslationPresenter(ApiProvider apiProvider, TranslationView translationView) {
        this.apiProvider = apiProvider;
        this.translationView = translationView;
    }

    public void getLanguageHub(String uiLanguage) {
        apiProvider.getLanguages(uiLanguage)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .doOnNext(response -> {
                    if (response.code() == 200 && response.body() != null) {
                        this.languageHub = response.body();
                        if (languageHub.languages.size() > 2) {
                            srcLanguage = languageHub.languages.entrySet()
                                    .iterator()
                                    .next()
                                    .getKey();
                            dstLanguage = languageHub.languages.entrySet()
                                    .iterator()
                                    .next()
                                    .getKey();

                            translationView.setSrcLanguage(languageHub.languages.get(srcLanguage));
                            translationView.setDstLanguage(languageHub.languages.get(dstLanguage));
                        }
                    }
                })
                .subscribe(new EmptySubscriber<>());
    }
}
