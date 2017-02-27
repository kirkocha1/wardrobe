package com.kirill.kochnev.homewardrope;

import android.app.Application;
import android.content.Context;

import com.kirill.kochnev.homewardrope.di.AppComponent;
import com.kirill.kochnev.homewardrope.di.DaggerAppComponent;
import com.kirill.kochnev.homewardrope.di.GreenDaoModule;

public class WardropeApplication extends Application {

    private static AppComponent component;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();
        context = this;
    }

    //
    private AppComponent buildComponent() {
        return DaggerAppComponent.builder().greenDaoModule(new GreenDaoModule(this)).build();
    }

    public static Context getContext() {
        return context;
    }

    public static AppComponent getComponent() {
        return component;
    }
}
