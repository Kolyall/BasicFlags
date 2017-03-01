package com.basicflags;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.basicflags.module.components.AppComponent;
import com.basicflags.module.components.DaggerAppComponent;
import com.basicflags.module.modules.AppModule;


/**
 * Created by Nikolay Unuchek on 13.10.2016.
 */
public class TheApplication extends MultiDexApplication {
    private AppComponent component;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        setupComponent();
    }

    public static Context getContext() {
        return context;
    }

    private void setupComponent() {
        component = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        component.inject(this);
    }

    public AppComponent getComponent() {
        if (component==null)
            setupComponent();
        return component;
    }

    public static AppComponent getComponent(Context context) {
        return ((TheApplication) context.getApplicationContext()).getComponent();
    }

    public static TheApplication get() {
        return (TheApplication)getContext().getApplicationContext();
    }
}
