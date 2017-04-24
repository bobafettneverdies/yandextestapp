package com.blahblah.yandextestapp.ui.translation;

import android.support.annotation.NonNull;

import com.blahblah.yandextestapp.api.ApiProvider;
import com.blahblah.yandextestapp.domain.language.Language;
import com.blahblah.yandextestapp.domain.language.LanguageHub;
import com.blahblah.yandextestapp.domain.translation.Translation;
import com.blahblah.yandextestapp.domain.translation.TranslationDto;
import com.blahblah.yandextestapp.realm.RealmTranslationRepository;
import com.blahblah.yandextestapp.utils.EmptySubscriber;

import java.util.List;

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
    private Translation translation;

    private final ApiProvider apiProvider;
    private final TranslationView translationView;
    private final RealmTranslationRepository translationRepository;

    @Inject
    public TranslationPresenter(ApiProvider apiProvider,
                                TranslationView translationView,
                                RealmTranslationRepository translationRepository) {
        this.apiProvider = apiProvider;
        this.translationView = translationView;
        this.translationRepository = translationRepository;
        translation = new Translation();
    }

    public void syncViewWithPresenterState(String uiLanguage, String translateLanguage) {
        if (languageHub == null) {
            getLanguageHub(uiLanguage, translateLanguage);
        } else if (translation.srcLanguage != null && translation.dstLanguage != null) {
            setLanguagesOnView();
            translationView.setSourceText(translation.source);
        }
    }

    public void translate(String source) {
        if (translation.srcLanguage != null && translation.dstLanguage != null) {
            translation.source = source;
            apiProvider.translate(source, translation.srcLanguage, translation.dstLanguage, null, null)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError(Throwable::printStackTrace)
                    .doOnNext(response -> {
                        if (response.code() == 200 && response.body() != null) {
                            TranslationDto translationDto = response.body();
                            translation.result = translationDto.toString();

                            if (!translation.source.isEmpty()) {
                                if (translationRepository.translationsExists()) {
                                    Translation latestTranslation = translationRepository.getLatest();
                                    if (translation.containsThatTranslation(latestTranslation)) {
                                        translation.id = latestTranslation.id;
                                        translationRepository.update(translation);
                                    } else if (!latestTranslation.containsThatTranslation(translation)) {
                                        translationRepository.add(translation);
                                    }
                                } else {
                                    translationRepository.add(translation);
                                }
                            }
                        } else {
                            translation.result = "";
                        }
                        translationView.setTranslation(translation.result);
                    })
                    .subscribe(new EmptySubscriber<>());
        }
    }

    public void swapLanguages() {
        if (languageHub != null && translation.srcLanguage != null && translation.dstLanguage != null) {
            String oldDstLanguage = translation.dstLanguage;
            translation.dstLanguage = translation.srcLanguage;
            translation.srcLanguage = oldDstLanguage;
            setLanguagesOnView();
            translationView.setSourceText(translation.result);
        }
    }

    public void setTranslation(@NonNull Translation translation) {
        this.translation.srcLanguage = translation.srcLanguage;
        this.translation.dstLanguage = translation.dstLanguage;
        this.translation.source = translation.source;
        this.translation.id = translation.id;
        this.translation.isFavorite = translation.isFavorite;
    }

    public void setTranslationSrcLanguage(String language) {
        if (language.equals(translation.dstLanguage)) {
            swapLanguages();
        } else {
            translation.srcLanguage = language;
            translation.source = "";
            setLanguagesOnView();
            translationView.setSourceText(translation.source);
        }
    }

    public void setTranslationDstLanguage(String language) {
        if (language.equals(translation.srcLanguage)) {
            swapLanguages();
        } else {
            translation.dstLanguage = language;
            setLanguagesOnView();
            translationView.setSourceText(translation.source);
        }
    }

    public List<Language> getLanguageList() {
        return languageHub != null ? languageHub.getLanguagesAsList() : null;
    }

    private void getLanguageHub(String uiLanguage, String translateLanguage) {
        apiProvider.getLanguages(uiLanguage)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(translationView::showLanguageHubQueryProgressBar)
                .doOnUnsubscribe(translationView::hideLanguageHubQueryProgressBar)
                .doOnError(Throwable::printStackTrace)
                .doOnNext(response -> {
                    if (response.code() == 200 && response.body() != null) {
                        this.languageHub = response.body();
                        if (languageHub.languages.size() > 2) {
                            translation.srcLanguage = uiLanguage;
                            translation.dstLanguage = translateLanguage;
                            setLanguagesOnView();
                        }
                    }
                })
                .subscribe(new EmptySubscriber<>());
    }

    private void setLanguagesOnView() {
        translationView.setSrcLanguage(languageHub.languages.get(translation.srcLanguage));
        translationView.setDstLanguage(languageHub.languages.get(translation.dstLanguage));
    }

}
