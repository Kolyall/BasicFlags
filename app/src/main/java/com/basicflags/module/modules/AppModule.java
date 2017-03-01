package com.basicflags.module.modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.basicflags.TheApplication;
import com.basicflags.module.annotations.ForApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Nikolay Unuchek on 07.09.2016.
 */
@Module
public class AppModule {
    public static final String SHARED_PREFS_NAME = "prefs";

    private TheApplication mApplication;

    public AppModule(@NonNull TheApplication mApplication) {
        this.mApplication = mApplication;
    }

    @Provides
    @NonNull
    @Singleton
    public Application provideApplication() {
        return mApplication;
    }


    @Provides
    Context provideContext(){
        return mApplication;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(@ForApplication Context context) {
        return context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

}
