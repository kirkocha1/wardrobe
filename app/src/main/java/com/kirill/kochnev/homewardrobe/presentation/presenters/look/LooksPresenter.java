package com.kirill.kochnev.homewardrobe.presentation.presenters.look;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kirill.kochnev.homewardrobe.AppConstants;
import com.kirill.kochnev.homewardrobe.WardrobeApplication;
import com.kirill.kochnev.homewardrobe.db.models.Look;
import com.kirill.kochnev.homewardrobe.enums.ViewMode;
import com.kirill.kochnev.homewardrobe.interactors.LooksInteractor;
import com.kirill.kochnev.homewardrobe.presentation.presenters.base.CompositeDisposableDelegate;
import com.kirill.kochnev.homewardrobe.presentation.presenters.base.IPaginator;
import com.kirill.kochnev.homewardrobe.presentation.presenters.base.ListLoaderDelegate;
import com.kirill.kochnev.homewardrobe.presentation.views.ILooksView;
import com.kirill.kochnev.homewardrobe.utils.bus.IdBus;
import com.kirill.kochnev.homewardrobe.utils.bus.StateBus;

import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kirill on 21.04.17.
 */

@InjectViewState
public class LooksPresenter extends MvpPresenter<ILooksView> implements IPaginator<Look> {

    public static final String TAG = "LooksPresenter";
    private ViewMode viewMode;
    private boolean isEdit;
    private long filterId;

    @NonNull
    private LooksInteractor interactor;
    @NonNull
    private final ListLoaderDelegate listDelegate = new ListLoaderDelegate(getViewState());
    @NonNull
    private final CompositeDisposableDelegate disposableDelegate = new CompositeDisposableDelegate();

    @Inject
    public LooksPresenter(
            @Named("filterId") long filterId,
            @Named("isEdit") boolean isEdit,
            @Named("mode") ViewMode mode,
            @NonNull IdBus idBus,
            @NonNull StateBus stateBus,
            @NonNull LooksInteractor interactor
    ) {
        this.interactor = interactor;
        this.isEdit = isEdit;
        initMode(mode, filterId);
    }

    @Override
    protected void onFirstViewAttach() {
        loadMoreData(AppConstants.DEFAULT_ID);
    }

    private void initMode(ViewMode mode, long wardropeId) {
        this.viewMode = mode;
        this.filterId = wardropeId;
        getViewState().setEditMode(isEdit);
        disposableDelegate.addToCompositeDisposable(
                interactor
                        .observeEditableModeChanges()
                        .subscribe(
                                state -> updateModeState(state.second),
                                e -> Log.e(TAG, e.getMessage())
                        )
        );
    }

    private void updateModeState(boolean mode) {
        isEdit = mode;
        getViewState().setEditMode(isEdit);
        if (viewMode != ViewMode.LOOK_MODE) {
            if (isEdit) {
                disposableDelegate.addToCompositeDisposable(
                        listDelegate.getDisposable(
                                interactor.getLooksByWardrope(AppConstants.DEFAULT_ID, AppConstants.DEFAULT_ID),
                                list -> {
                                    getViewState().onLoadFinished(list);
                                    setIds(list);
                                },
                                e -> Log.e(TAG, e.getMessage())
                        )
                );
            } else {
                loadMoreData(AppConstants.DEFAULT_ID);
            }
        }
    }

    private void setIds(List<Look> list) {
        HashSet<Long> ids = new HashSet<>();
        for (Look look : list) {
            if (look.getWardrobeId() != null && look.getWardrobeId() == filterId) {
                ids.add(look.getId());
            }
        }
        getViewState().markAdapterViews(ids);
    }

    @Override
    public void loadMoreData(long lastId) {
        Log.d(TAG, "loadMoreData");
        disposableDelegate.addToCompositeDisposable(
                listDelegate.getDisposable(
                        interactor.getLooksByWardrope(
                                lastId, viewMode == ViewMode.WARDROBE_MODE && isEdit ?
                                        AppConstants.DEFAULT_ID : filterId),
                        list -> getViewState().onLoadFinished(list),
                        e -> Log.e(TAG, e.getMessage())
                )
        );
    }

    @Override
    public void onLongItemClick(Look model) {
        if (viewMode == ViewMode.LOOK_MODE) {
            disposableDelegate.addToCompositeDisposable(
                    interactor.deleteLook((Look) model)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(isDel -> {
                                getViewState().deleteListItem((Look) model);
                            })
            );
        }
    }

    @Override
    public void onItemClick(Look model) {
        if (viewMode != ViewMode.LOOK_MODE && isEdit) {
            disposableDelegate.addToCompositeDisposable(
                    interactor
                            .sendLookIdWithMode(ViewMode.LOOK_MODE, model.getId())
                            .subscribe(
                                    () -> {
                                    },
                                    e -> Log.e(TAG, e.getMessage())
                            )
            );

        } else {
            getViewState().navigateToUpdateLookView(model.getId());
        }
    }

    @Override
    public void putListItem(long id) {
        disposableDelegate.addToCompositeDisposable(
                listDelegate.getDisposable(interactor.getLook(id),
                        item -> getViewState().invalidateListItem(item),
                        e -> Log.e(TAG, e.getMessage())
                )
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposableDelegate.unsubscribe();
        WardrobeApplication.getComponentHolder().clearLookComponent();
    }
}
