package com.blahblah.yandextestapp.api;

import android.support.annotation.NonNull;

import com.blahblah.yandextestapp.domain.language.LanguageHub;
import com.blahblah.yandextestapp.domain.translation.TranslationDto;

import javax.inject.Singleton;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by Dmitrii Komiakov
 * komiakov.dmitrii@gmail.com
 * komyakovds@byndyusoft.com
 * on 14.04.2017.
 */

@Singleton
public interface ApiProvider {
    Observable<Response<LanguageHub>> getLanguages(String uiLanguage);

    Observable<Response<TranslationDto>> translate(@NonNull String text, @NonNull String srcLanguage, @NonNull String dstLanguage, String format, String options);
}
