package com.basicflags.module;


import com.basicflags.TheApplication;
import com.basicflags.module.components.AppComponent;
import com.basicflags.module.delegate.PicassoDelegate;
import com.basicflags.ui.activities.MainActivity;
import com.basicflags.ui.activities.base.BaseRxActivity;

public class DependencyInjection {

    private static AppComponent getComponent() {return TheApplication.get().getComponent();}

    public static void inject(BaseRxActivity obj) {
        getComponent().inject(obj);
    }

    public static void inject(MainActivity obj) {
        getComponent().inject(obj);
    }

    public static void inject(PicassoDelegate obj) {
        getComponent().inject(obj);
    }

}
