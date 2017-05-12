package com.kirill.kochnev.homewardrope.mvp.presenters.look;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.interactors.interfaces.ILooksInteractor;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseMvpPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IUpdateLook;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kirill on 03.05.17.
 */

@InjectViewState
public class UpdateLookPresenter extends BaseMvpPresenter<IUpdateLook> {

    public static final String TAG = "UpdateLookPresenter";

    private long lookId;
    @Inject
    protected ILooksInteractor interactor;

    public UpdateLookPresenter(long lookId) {
        WardropeApplication.getLookComponent().inject(this);
        this.lookId = lookId;
    }

    @Override
    public void attachView(IUpdateLook view) {
        init(lookId);
        super.attachView(view);
    }

    private void init(long lookId) {
        interactor.getLook(lookId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(look -> getViewState().setLookData(look),
                        e -> Log.e(TAG, e.getMessage()));
    }

    public void saveLook(String name, String tag) {
        unsubscribeOnDestroy(interactor.saveLook(name, tag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isPut -> getViewState().onSave()));
    }
}
