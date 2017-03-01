package com.basicflags.module.components;


import com.basicflags.TheApplication;
import com.basicflags.module.annotations.ForApplication;
import com.basicflags.module.delegate.PicassoDelegate;
import com.basicflags.module.modules.AppModule;
import com.basicflags.module.modules.GsonModule;
import com.basicflags.module.modules.NetworkApiModule;
import com.basicflags.module.modules.OkHttpClientModule;
import com.basicflags.module.modules.OkHttpDownloaderModule;
import com.basicflags.module.modules.PicassoModule;
import com.basicflags.module.modules.RxApiModule;
import com.basicflags.ui.activities.MainActivity;
import com.basicflags.ui.activities.base.BaseRxActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Nikolay Unuchek on 07.09.2016.
 */
@ForApplication
@Singleton
@Component(
        modules = {AppModule.class,
                OkHttpClientModule.class,
                NetworkApiModule.class,
                RxApiModule.class,
                OkHttpDownloaderModule.class,
                PicassoModule.class,
                GsonModule.class
        })
public interface AppComponent {

    void inject(TheApplication obj);

    void inject(MainActivity obj);

    void inject(PicassoDelegate obj);

    void inject(BaseRxActivity obj);
}