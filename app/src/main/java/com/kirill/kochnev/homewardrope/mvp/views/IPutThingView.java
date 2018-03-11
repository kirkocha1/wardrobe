package com.kirill.kochnev.homewardrope.mvp.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kirill.kochnev.homewardrope.db.models.Thing;

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
