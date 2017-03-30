package com.kirill.kochnev.homewardrope.mvp.views.interfaces;

import android.graphics.Bitmap;
import android.net.Uri;

import com.arellomobile.mvp.MvpView;
import com.kirill.kochnev.homewardrope.db.models.Thing;

public interface IAddUpdateThingView extends MvpView {

    void setImage(Bitmap bitmap);

    void setImageUri(Uri uri);

    void sendMakePhotoIntent(Uri uri);

    void onSave();

    void showError(String error);

    void updateView(Thing model);
}
