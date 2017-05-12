package com.kirill.kochnev.homewardrope.mvp.presenters.look;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.enums.CollageMode;
import com.kirill.kochnev.homewardrope.interactors.interfaces.ICollageInteractor;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseMvpPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.ICollageView;

import java.util.HashSet;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kirill on 27.04.17.
 */

@InjectViewState
public class CollagePresenter extends BaseMvpPresenter<ICollageView> {

    public static final String TAG = "CollagePresenter";

    @Inject
    protected ICollageInteractor interactor;

    public CollagePresenter(HashSet<Long> thingIds) {
        WardropeApplication.getLookComponent().inject(this);
        interactor.getImages(thingIds).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(cache -> {
                    getViewState().constructView(cache, CollageMode.getByNum(cache.size()));
                }, e -> Log.e(TAG, e.getMessage()));
    }

}
