package com.kirill.kochnev.homewardrobe;

import android.app.Application;
import android.os.StrictMode;
import android.support.annotation.NonNull;

import com.kirill.kochnev.homewardrobe.di.ComponentHolder;

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

    @NonNull
    public static ComponentHolder getComponentHolder() {
        return componentHolder;
    }
}
