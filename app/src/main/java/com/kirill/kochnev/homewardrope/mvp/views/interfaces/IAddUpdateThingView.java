package com.kirill.kochnev.homewardrope.mvp.views.interfaces;

import android.graphics.Bitmap;

import com.arellomobile.mvp.MvpView;

public interface IAddUpdateThingView extends MvpView {

    void setImage(Bitmap bitmap);
}
