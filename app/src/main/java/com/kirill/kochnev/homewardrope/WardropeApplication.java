package com.kirill.kochnev.homewardrope;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.kirill.kochnev.homewardrope.di.components.AppComponent;
import com.kirill.kochnev.homewardrope.di.components.DaggerAppComponent;
import com.kirill.kochnev.homewardrope.di.components.LookComponent;
import com.kirill.kochnev.homewardrope.di.modules.DbModule;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import java.io.File;

public class WardropeApplication extends Application {

    public static final String TAG = "WardropeApplication";
    private static AppComponent component;
    private static LookComponent lookComponent;

    private static Context context;
    private static Picasso picasso;

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();
        context = getApplicationContext();
        picasso = new Picasso.Builder(context)
                .memoryCache(new LruCache(5 * AppConstants.BYTES_MB_COEFF))
                .build();
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

    public static void loadImage(String url, ImageView imageView) {
        Uri uriToFile = url != null ? Uri.fromFile(new File(url)) : null;
        Picasso.with(getContext()).load(uriToFile).placeholder(R.drawable.image_placeholder).into(imageView);
    }

    public static Context getContext() {
        return context;
    }

    public static AppComponent getComponent() {
        return component;
    }
}
