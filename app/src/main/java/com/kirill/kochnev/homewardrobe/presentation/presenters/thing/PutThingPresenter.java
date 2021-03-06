package com.kirill.kochnev.homewardrobe.presentation.presenters.thing;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kirill.kochnev.homewardrobe.AppConstants;
import com.kirill.kochnev.homewardrobe.interactors.PutThingsInteractor;
import com.kirill.kochnev.homewardrobe.presentation.presenters.base.CompositeDisposableDelegate;
import com.kirill.kochnev.homewardrobe.presentation.views.IPutThingView;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@InjectViewState
public class PutThingPresenter extends MvpPresenter<IPutThingView> {

    public static final String TAG = "PutThingPresenter";

    private @NonNull final PutThingsInteractor interactor;
    private long thingId;

    @NonNull
    private final CompositeDisposableDelegate disposableDelegate = new CompositeDisposableDelegate();

    @Inject
    PutThingPresenter(
            @Named("thingId") long id,
            @NonNull final PutThingsInteractor interactor
    ) {
        this.interactor = interactor;
        thingId = id;
    }

    @Override
    protected void onFirstViewAttach() {
        initValues(thingId);
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
