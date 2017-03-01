package com.basicflags.module.modules;

import android.support.annotation.NonNull;

import com.basicflags.module.service.APIService;
import com.google.gson.Gson;

import java.util.HashMap;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nick Unuchek (skype: kolyall) on 04.10.2016.
 */
@Module
public class NetworkApiModule {
    public static final String KEY_API = "API";

    private HashMap<String, APIService> mServices = new HashMap<>();

    @Provides @Named(KEY_API)
    @NonNull
    APIService providesPerformanceService(@Named(GsonModule.LOWER_CASE_WITH_UNDERSCORES) Gson gson,
                                                  @Named(OkHttpClientModule.KEY_API_OKHTTPCLIENT) OkHttpClient okHttpClient) {

        APIService service = mServices.get(APIService.BASE_URL);
        if (service == null) {
            Converter.Factory factory = GsonConverterFactory.create(gson);
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(APIService.BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(factory);
            Retrofit retrofit = builder.build();
            service = retrofit.create(APIService.class);
            mServices.put(APIService.BASE_URL, service);
        }

        return service;
    }
}