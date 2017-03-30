package com.kirill.kochnev.homewardrope;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.kirill.kochnev.homewardrope.di.AppComponent;
import com.kirill.kochnev.homewardrope.di.DaggerAppComponent;
import com.kirill.kochnev.homewardrope.di.GreenDaoModule;
import com.squareup.picasso.Picasso;

import java.io.File;

public class WardropeApplication extends Application {

    public static final String TAG = "WardropeApplication";
    private static AppComponent component;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();
        context = this;
    }

    private AppComponent buildComponent() {
        return DaggerAppComponent.builder().greenDaoModule(new GreenDaoModule(this)).build();
    }

    public static void loadImage(String url, ImageView imageView) {
        Log.e(TAG, "path to icon: " + url);
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
