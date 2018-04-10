package com.kirill.kochnev.homewardrope.presentation.presenters.look;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.enums.CreationLookState;
import com.kirill.kochnev.homewardrope.interactors.LooksInteractor;
import com.kirill.kochnev.homewardrope.presentation.presenters.base.CompositeDisposableDelegate;
import com.kirill.kochnev.homewardrope.presentation.views.IFirstStepCreationLookView;
import com.kirill.kochnev.homewardrope.utils.LookExeception;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kirill on 27.04.17.
 */

@InjectViewState
public class CreationLookPresenter extends MvpPresenter<IFirstStepCreationLookView> {

    public static final String TAG = "CreationLook";
    private long lookId;
    private @NonNull final LooksInteractor interactor;

    @NonNull
    private final CompositeDisposableDelegate disposableDelegate = new CompositeDisposableDelegate();

    @Inject
    CreationLookPresenter(
            @Named("lookId") final long id,
            @NonNull final LooksInteractor interactor
    ) {
        this.interactor = interactor;
        this.lookId = id;
    }

    @Override
    protected void onFirstViewAttach() {
        if (lookId == AppConstants.DEFAULT_ID) {
            interactor.initializeLook();
        } else {
            disposableDelegate.addToCompositeDisposable(
                    interactor
                            .getLook(lookId)
                            .subscribe(
                                    look -> {
                                    },
                                    e -> {
                                        Log.e(TAG, e.getMessage());
                                    }
                            )
            );
        }
        disposableDelegate.addToCompositeDisposable(
                interactor
                        .observeWardrobeIdOrThingsId()
                        .subscribe(
                                idPair -> {
                                    switch (idPair.first) {
                                        case THING_MODE:
                                            interactor.addThingId(idPair.second);
                                            break;
                                        case WARDROBE_MODE:
                                            interactor.addWardrobeId(idPair.second);
                                            break;
                                    }
                                },
                                e -> Log.e(TAG, e.getMessage())
                        )
        );
    }

    public void clearIds() {
        clearIds(false);
    }

    public void clearIds(boolean isThings) {
        interactor.clear();
        if (isThings) {
            interactor.addWardrobeId(null);
        }
    }

    public void processLook(String name, String tag, Bitmap bitmap) {
        disposableDelegate.addToCompositeDisposable(interactor.saveLookWithBitmap(name, tag, bitmap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> getViewState().onSuccess(result.getId()),
                        e -> Log.e(TAG, e.getMessage()))
        );
    }

    public void save() {
        disposableDelegate.addToCompositeDisposable(
                interactor
                        .getLook()
                        .subscribe(look -> getViewState().showSaveDialog(look.getName(), look.getTag()),
                                e -> Log.e(TAG, e.getMessage())
                        )
        );
    }

    public void startCreationProcess() {
        disposableDelegate.addToCompositeDisposable(
                interactor
                        .startCreation()
                        .subscribe(ids -> getViewState().openCollageFragment(ids),
                                e -> {
                                    if (e instanceof LookExeception) {
                                        getViewState().showError(((LookExeception) e).isNotEnough());
                                    } else {
                                        Log.e(TAG, e.getMessage());
                                    }
                                })
        );
    }

    public void resolveBtnsState(CreationLookState state) {
        switch (state) {
            case ALL_THINGS:
            case WARDROBES:
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposableDelegate.unsubscribe();
    }
}
