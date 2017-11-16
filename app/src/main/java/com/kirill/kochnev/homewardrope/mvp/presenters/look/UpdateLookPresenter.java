package com.kirill.kochnev.homewardrope.mvp.presenters.look;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.interactors.LooksInteractor;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.CompositeDisposableDelegate;
import com.kirill.kochnev.homewardrope.mvp.views.IUpdateLook;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kirill on 03.05.17.
 */

@InjectViewState
public class UpdateLookPresenter extends MvpPresenter<IUpdateLook> {

    public static final String TAG = "UpdateLookPresenter";
    public boolean isNeedToAttach = false;

    @Inject
    protected LooksInteractor interactor;

    @NonNull
    private final CompositeDisposableDelegate disposableDelegate = new CompositeDisposableDelegate();

    public UpdateLookPresenter(long lookId) {
        WardropeApplication.getComponentHolder().getLookComponent().inject(this);
        init(lookId);
    }


    @Override
    public void attachView(IUpdateLook view) {
        super.attachView(view);
        if (isNeedToAttach) {
            disposableDelegate.addToCompositeDisposable(
                    interactor
                            .getLook()
                            .subscribe(look -> getViewState().setLookData(look), e -> Log.e(TAG, e.getMessage()))
            );
        }
    }

    @Override
    public void detachView(IUpdateLook view) {
        isNeedToAttach = true;
        super.detachView(view);
    }

    private void init(long lookId) {
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
                        })
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposableDelegate.unsubscribe();
    }
}
