package com.kirill.kochnev.homewardrope;

import android.app.Application;
import android.os.StrictMode;

import com.kirill.kochnev.homewardrope.di.ComponentHolder;

public class WardropeApplication extends Application {

    public static final String TAG = "WardropeApplication";
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
