package com.kirill.kochnev.homewardrope;

import android.app.Application;

import com.kirill.kochnev.homewardrope.di.AppComponent;
import com.kirill.kochnev.homewardrope.di.DaggerAppComponent;
import com.kirill.kochnev.homewardrope.di.GreenDaoModule;

public class WardropeApplication extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();
    }

    //
    private AppComponent buildComponent() {
        return DaggerAppComponent.builder().greenDaoModule(new GreenDaoModule(this)).build();
    }

    public static AppComponent getComponent() {
        return component;
    }
}
