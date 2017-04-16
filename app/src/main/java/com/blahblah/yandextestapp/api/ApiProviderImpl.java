package com.blahblah.yandextestapp.api;

import android.support.annotation.NonNull;

import com.blahblah.yandextestapp.domain.language.LanguageHub;
import com.blahblah.yandextestapp.domain.translation.TranslationDto;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;

/**
 * Created by Dmitrii Komiakov
 * komiakov.dmitrii@gmail.com
 * komyakovds@byndyusoft.com
 * on 14.04.2017.
 */
public class ApiProviderImpl implements ApiProvider {

    private ApiInterface apiInterface;
    private RetrofitErrorHandler<Object> errorHandler;
    private String apiKey;

    @Inject
    public ApiProviderImpl(ApiInterface apiInterface, RetrofitErrorHandler<Object> errorHandler, String apiKey) {
        this.apiInterface = apiInterface;
        this.errorHandler = errorHandler;
        this.apiKey = apiKey;
    }

    @Override
    public Observable<Response<LanguageHub>> getLanguages(String uiLanguage) {
        return apiInterface.getLanguages(apiKey, uiLanguage).compose(errorHandler.cast());
    }

    @Override
    public Observable<Response<TranslationDto>> translate(@NonNull String text, @NonNull String srcLanguage, @NonNull String dstLanguage, String format, String options) {
        return apiInterface.translate(apiKey, text, String.format("%s-%s", srcLanguage, dstLanguage), format, options);
    }
}
