package com.kirill.kochnev.homewardrope.mvp.views.interfaces;

import android.graphics.Bitmap;
import android.net.Uri;

import com.arellomobile.mvp.MvpView;

public interface IAddUpdateThingView extends MvpView {

    void setImage(Bitmap bitmap);

    void sendMakePhotoIntent(Uri uri);

    void onSave();
}
