package com.kirill.kochnev.homewardrope.mvp.presenters.thing;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.interactors.AddUpdateThingsInteractor;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.CompositeDisposableDelegate;
import com.kirill.kochnev.homewardrope.mvp.views.IAddUpdateThingView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class AddUpdateThingPresenter extends MvpPresenter<IAddUpdateThingView> {

    public static final String TAG = "AddUpdateThingPresenter";

    @Inject
    AddUpdateThingsInteractor interactor;

    @NonNull
    private final CompositeDisposableDelegate disposableDelegate = new CompositeDisposableDelegate();

    public AddUpdateThingPresenter(long id) {
        WardropeApplication.getComponentHolder().getMainComponent().inject(this);
        initValues(id);
    }

    private void initValues(long id) {
        disposableDelegate.addToCompositeDisposable(
                interactor
                        .getThing(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                model -> getViewState().showThing(model),
                                e -> Log.e(TAG, e.getMessage())
                        )
        );
    }

    public void saveThing(String name, String tag) {
        disposableDelegate.addToCompositeDisposable(
                interactor
                        .saveThing(name, tag)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                result -> {
                                    Intent intent = new Intent();
                                    intent.putExtra(AppConstants.ADD_UPDATED_ID, result.getId());
                                    getViewState().onSave(intent);
                                },
                                e -> Log.e(TAG, e.getMessage())
                        )
        );
    }

    public void createUri() {
        disposableDelegate.addToCompositeDisposable(
                interactor
                        .getPhotoUri()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(uri -> getViewState().sendMakePhotoIntent(uri))
        );
    }

    public void processImage() {
        disposableDelegate.addToCompositeDisposable(
                interactor
                        .saveImages()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                img -> getViewState().setImage(img),
                                ex -> getViewState().showError(ex.getMessage())
                        )
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposableDelegate.unsubscribe();
    }
}
