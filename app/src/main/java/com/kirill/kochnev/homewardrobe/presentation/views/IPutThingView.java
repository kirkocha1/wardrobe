package com.kirill.kochnev.homewardrobe.presentation.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kirill.kochnev.homewardrobe.db.models.Thing;

public interface IPutThingView extends MvpView {

    void setImage(Bitmap bitmap);

    void setImageUri(Uri uri);

    @StateStrategyType(SkipStrategy.class)
    void sendMakePhotoIntent(Uri uri);

    @StateStrategyType(SkipStrategy.class)
    void onSave(Intent intent);

    void showError(String error);

    void showThing(Thing thing);
}
