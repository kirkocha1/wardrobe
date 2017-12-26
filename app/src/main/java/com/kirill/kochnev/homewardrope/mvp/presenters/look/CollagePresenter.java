package com.kirill.kochnev.homewardrope.mvp.presenters.look;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kirill.kochnev.homewardrope.WardrobeApplication;
import com.kirill.kochnev.homewardrope.enums.CollageMode;
import com.kirill.kochnev.homewardrope.interactors.CollageInteractor;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.CompositeDisposableDelegate;
import com.kirill.kochnev.homewardrope.mvp.views.ICollageView;

import java.util.HashSet;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kirill on 27.04.17.
 */

@InjectViewState
public class CollagePresenter extends MvpPresenter<ICollageView> {

    public static final String TAG = "CollagePresenter";

    @Inject
    protected CollageInteractor interactor;

    @NonNull
    private final CompositeDisposableDelegate disposableDelegate = new CompositeDisposableDelegate();

    public CollagePresenter(HashSet<Long> thingIds) {
        WardrobeApplication.getComponentHolder().getLookComponent().inject(this);
        disposableDelegate.addToCompositeDisposable(
                interactor
                        .getImages(thingIds)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                cache -> getViewState().constructView(cache, CollageMode.getByNum(cache.size())),
                                e -> Log.e(TAG, e.getMessage())
                        )
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposableDelegate.unsubscribe();
    }
}
