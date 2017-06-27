package com.kirill.kochnev.homewardrope;

import android.app.Application;
import android.os.StrictMode;

import com.kirill.kochnev.homewardrope.di.components.AppComponent;
import com.kirill.kochnev.homewardrope.di.components.DaggerAppComponent;
import com.kirill.kochnev.homewardrope.di.components.LookComponent;
import com.kirill.kochnev.homewardrope.di.modules.DbModule;

public class WardropeApplication extends Application {

    public static final String TAG = "WardropeApplication";
    private static AppComponent component;
    private static LookComponent lookComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .build());
        }
        component = buildComponent();
    }

    private AppComponent buildComponent() {
        return DaggerAppComponent.builder().dbModule(new DbModule(this)).build();
    }

    public static LookComponent getLookComponent() {
        if (lookComponent == null) {
            lookComponent = component.plusComponent();
        }
        return lookComponent;
    }

    public static void clearLookComponent() {
        lookComponent = null;
    }

    public static AppComponent getComponent() {
        return component;
    }
}
