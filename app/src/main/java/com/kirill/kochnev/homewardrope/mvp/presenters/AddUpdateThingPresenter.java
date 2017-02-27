package com.kirill.kochnev.homewardrope.mvp.presenters;

import android.graphics.Bitmap;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IAddUpdateThingView;


@InjectViewState
public class AddUpdateThingPresenter extends MvpPresenter<IAddUpdateThingView> {

    public void saveThing(String name) {

    }

    public void processImage(Bitmap pic) {
        getViewState().setImage(pic);
    }

}
