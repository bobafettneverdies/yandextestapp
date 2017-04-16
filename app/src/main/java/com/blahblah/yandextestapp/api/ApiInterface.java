package com.blahblah.yandextestapp.api;

import android.support.annotation.NonNull;

import com.blahblah.yandextestapp.domain.language.LanguageHub;
import com.blahblah.yandextestapp.domain.translation.TranslationDto;

import retrofit2.Response;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by 123 on 10.04.2017.
 */

public interface ApiInterface {

    @POST("tr.json/getLangs")
    Observable<Response<LanguageHub>> getLanguages(@NonNull @Query("key") String apiKey, @Query("ui") String uiLanguage);

    @POST("tr.json/translate")
    Observable<Response<TranslationDto>> translate(@NonNull @Query("key") String apiKey,
                                                   @NonNull @Query("text") String text,
                                                   @NonNull @Query("lang") String language,
                                                   @Query("format") String format,
                                                   @Query("options") String options);

}
