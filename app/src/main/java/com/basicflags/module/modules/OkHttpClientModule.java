package com.basicflags.module.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.basicflags.BuildConfig;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Nikolay Unuchek on 08.09.2016.
 */
@Module
public class OkHttpClientModule {
    public static final String KEY_API_OKHTTPCLIENT = "API_OKHTTPCLIENT";

    @Provides @Named(KEY_API_OKHTTPCLIENT)
    @NonNull
    @Singleton
    OkHttpClient providesOkHttpClient(Context context) {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG)
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
    }
}