package com.kirill.kochnev.homewardrope.mvp.presenters.look;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.enums.CreationLookState;
import com.kirill.kochnev.homewardrope.interactors.interfaces.ILooksInteractor;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseMvpPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IFirstStepCreationLookView;
import com.kirill.kochnev.homewardrope.utils.LookExeception;
import com.kirill.kochnev.homewardrope.utils.bus.IdBus;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kirill on 27.04.17.
 */

@InjectViewState
public class CreationLookPresenter extends BaseMvpPresenter<IFirstStepCreationLookView> {

    public static final String TAG = "CreationLook";

    @Inject
    IdBus bus;

    @Inject
    ILooksInteractor interactor;

    public CreationLookPresenter(long id) {
        WardropeApplication.getLookComponent().inject(this);
        if (id == AppConstants.DEFAULT_ID) {
            interactor.initializeLook();
        }
        unsubscribeOnDestroy(bus.register(idPair -> {
            switch (idPair.first) {
                case THING_MODE:
                    interactor.addThingId(idPair.second);
                    break;
                case WARDROPE_MODE:
                    interactor.addWardropeId(idPair.second);
                    break;
            }

        }));
    }

    public void clearIds() {
        clearIds(false);
    }

    public void clearIds(boolean isThings) {
        interactor.clear();
        if (isThings) {
            interactor.addWardropeId(null);
        }
    }

    public void processLook(String name, String tag, Bitmap bitmap) {
        unsubscribeOnDestroy(interactor.saveLookWithBitmap(name, tag, bitmap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                            Intent intent = new Intent();
                            intent.putExtra(AppConstants.ADD_UPDATED_ID, result.getId());
                            getViewState().onSuccess(intent);
                        },
                        e -> Log.e(TAG, e.getMessage())));
    }

    public void save() {
        unsubscribeOnDestroy(interactor.getLook()
                .subscribe(look -> getViewState().showSaveDialog(look.getName(), look.getTag()),
                        e -> Log.e(TAG, e.getMessage())));
    }

    public void startCreationProcess() {
        unsubscribeOnDestroy(interactor.startCreation().subscribe(ids -> getViewState().openCollageFragment(ids),
                e -> {
                    if (e instanceof LookExeception) {
                        getViewState().showError(((LookExeception) e).isNotEnough());
                    } else {
                        Log.e(TAG, e.getMessage());
                    }
                }));
    }

    public void resolveBtnsState(CreationLookState state) {
        switch (state) {
            case ALL_THINGS:
            case WARDROPES:
                getViewState().setBtnsState(true, false, false);
                break;
            case START:
                getViewState().setBtnsState(false, false, true);
                break;
            case COLLAGE:
                getViewState().setBtnsState(false, true, false);
                break;
        }
    }
}
