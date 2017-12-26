package com.kirill.kochnev.homewardrope;

import android.app.Application;
import android.os.StrictMode;

import com.kirill.kochnev.homewardrope.di.ComponentHolder;

public class WardrobeApplication extends Application {

    public static final String TAG = "WardrobeApplication";
    private static ComponentHolder componentHolder;

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
        componentHolder = new ComponentHolder(this);
    }

    public static ComponentHolder getComponentHolder() {
        return componentHolder;
    }
}