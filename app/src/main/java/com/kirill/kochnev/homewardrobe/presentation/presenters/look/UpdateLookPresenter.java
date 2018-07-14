package com.kirill.kochnev.homewardrobe.presentation.presenters.look;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kirill.kochnev.homewardrobe.AppConstants;
import com.kirill.kochnev.homewardrobe.interactors.LooksInteractor;
import com.kirill.kochnev.homewardrobe.presentation.presenters.base.CompositeDisposableDelegate;
import com.kirill.kochnev.homewardrobe.presentation.views.IUpdateLook;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kirill on 03.05.17.
 */

@InjectViewState
public class UpdateLookPresenter extends MvpPresenter<IUpdateLook> {

    public static final String TAG = "UpdateLookPresenter";

    private final long lookId;
    private @NonNull final LooksInteractor interactor;

    @NonNull
    private final CompositeDisposableDelegate disposableDelegate = new CompositeDisposableDelegate();

    @Inject
    UpdateLookPresenter(
            @Named("lookId") long lookId,
            @NonNull final LooksInteractor interactor
    ) {
        this.interactor = interactor;
        this.lookId = lookId;
    }

    @Override
    protected void onFirstViewAttach() {
        updateListItem(lookId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposableDelegate.unsubscribe();
    }

    public void updateListItem(long lookId) {
        disposableDelegate.addToCompositeDisposable(
                interactor
                        .getLook(lookId)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(look -> getViewState().setLookData(look),
                                e -> Log.e(TAG, e.getMessage())
                        )
        );
    }

    public void saveLook(String name, String tag) {
        disposableDelegate.addToCompositeDisposable(
                interactor
                        .saveLook(name, tag)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(isPut -> {
                                    Intent intent = new Intent();
                                    intent.putExtra(AppConstants.ADD_UPDATED_ID, isPut.getId());
                                    getViewState().onSave(intent);
                                },
                                e -> Log.e(TAG, e.getMessage())
                        )
        );
    }

    public void updateClick() {
        disposableDelegate.addToCompositeDisposable(
                interactor
                        .getLook()
                        .subscribe(
                                look -> getViewState().goToUpdateLookScreen(look),
                                e -> Log.e(TAG, e.getMessage())
                        )
        );
    }
}
