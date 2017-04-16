package com.blahblah.yandextestapp.ui.translation;

import com.blahblah.yandextestapp.api.ApiProvider;
import com.blahblah.yandextestapp.domain.language.LanguageHub;
import com.blahblah.yandextestapp.domain.translation.TranslationDto;
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

    public void getLanguageHub(String uiLanguage, String enLanguage) {
        apiProvider.getLanguages(uiLanguage)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .doOnNext(response -> {
                    if (response.code() == 200 && response.body() != null) {
                        this.languageHub = response.body();
                        if (languageHub.languages.size() > 2) {
                            srcLanguage = uiLanguage;
                            dstLanguage = enLanguage;

                            setLanguagesOnView();
                        }
                    }
                })
                .subscribe(new EmptySubscriber<>());
    }

    public void swapLanguages() {
        if (languageHub != null && srcLanguage != null && dstLanguage != null) {
            String oldDstLanguage = dstLanguage;
            dstLanguage = srcLanguage;
            srcLanguage = oldDstLanguage;

            setLanguagesOnView();
        }
    }

    public void translate(String source) {
        if (srcLanguage != null && dstLanguage != null) {
            apiProvider.translate(source, srcLanguage, dstLanguage, null, null)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError(Throwable::printStackTrace)
                    .doOnNext(response -> {
                        if (response.code() == 200 && response.body() != null) {
                            TranslationDto translationDto = response.body();
                            translationView.setTranslation(translationDto.toString());
                        } else {
                            translationView.setTranslation("");
                        }
                    })
                    .subscribe(new EmptySubscriber<>());
        }
    }

    private void setLanguagesOnView() {
        translationView.setSrcLanguage(languageHub.languages.get(srcLanguage));
        translationView.setDstLanguage(languageHub.languages.get(dstLanguage));
    }

}
