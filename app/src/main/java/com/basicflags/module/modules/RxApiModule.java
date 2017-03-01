package com.basicflags.module.modules;

import android.support.annotation.NonNull;

import com.basicflags.module.service.APIService;
import com.basicflags.service.RxApiService;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.basicflags.module.modules.NetworkApiModule.KEY_API;

/**
 * Created by Nikolay Unuchek on 08.09.2016.
 */
@Module
public class RxApiModule {
    @Singleton
    @Provides
    @NonNull
    RxApiService providesRxApiService(@Named(KEY_API) APIService performanceServiceApi) {
        return new RxApiService(performanceServiceApi);
    }
}