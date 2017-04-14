package com.blahblah.yandextestapp.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.blahblah.yandextestapp.BuildConfig;
import com.blahblah.yandextestapp.api.ApiInterface;
import com.blahblah.yandextestapp.api.ApiProvider;
import com.blahblah.yandextestapp.api.ApiProviderImpl;
import com.blahblah.yandextestapp.api.RetrofitErrorHandler;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 123 on 13.04.2017.
 */

@Module
public class NetworkModule {

    private static final String TAG = NetworkModule.class.getName();

    private static final int TIMEOUT = 15;

    @NonNull
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(@NonNull final Context context) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.connectTimeout(TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(TIMEOUT, TimeUnit.SECONDS);

        clientBuilder.cache(new Cache(context.getCacheDir(), Long.MAX_VALUE));

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(interceptor);
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(5);
        clientBuilder.dispatcher(dispatcher);

        return clientBuilder.build();
    }

    @Provides
    @NonNull
    @Singleton
    Retrofit provideRetrofit(@NonNull final OkHttpClient httpClient, @NonNull final Gson gson) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BuildConfig.YANDEX_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        builder.client(httpClient);

        return builder.build();
    }

    @NonNull
    @Provides
    @Singleton
    ApiInterface provideApiInterface(@NonNull final Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);
    }

    @NonNull
    @Provides
    @Singleton
    ApiProvider provideApiProvider(@NonNull Context context, @NonNull ApiInterface apiInterface) {
        String apiKey = "";

        InputStream inputStream = null;

        try {
            inputStream = context.getAssets().open(BuildConfig.YANDEX_API_KEY_FILE_NAME);
            Scanner scanner = new Scanner(inputStream);
            apiKey = scanner.hasNext() ? scanner.next() : null;
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "onCreate: " + apiKey);
        } catch (IOException e) {
            Log.e(TAG, "Problem with opening " + BuildConfig.YANDEX_API_KEY_FILE_NAME);
            e.printStackTrace();
        } catch (NullPointerException e) {
            Log.e(TAG, String.format(
                            "If u want to re-use this project code u need to add file %s with your Yandex Translate API key to assets folder",
                            BuildConfig.YANDEX_API_KEY_FILE_NAME));
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, "Problem with closing " + BuildConfig.YANDEX_API_KEY_FILE_NAME);
                    e.printStackTrace();
                }
            }
        }

        return new ApiProviderImpl(apiInterface, new RetrofitErrorHandler<>(), apiKey);
    }
}
