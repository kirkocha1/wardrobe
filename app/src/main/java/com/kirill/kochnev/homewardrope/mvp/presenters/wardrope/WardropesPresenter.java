package com.kirill.kochnev.homewardrope.mvp.presenters.wardrope;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.interactors.WardrobesInteractor;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.CompositeDisposableDelegate;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.IPaginator;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.ListLoaderDelegate;
import com.kirill.kochnev.homewardrope.mvp.views.IWardropeView;
import com.kirill.kochnev.homewardrope.utils.bus.IdBus;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by kirill on 30.03.17.
 */
@InjectViewState
public class WardropesPresenter extends MvpPresenter<IWardropeView> implements IPaginator {
    private static final String TAG = "WardropesPresenter";

    @NonNull
    private IdBus bus;

    @NonNull
    private WardrobesInteractor interactor;

    @NonNull
    private final ViewMode mode;

    @NonNull
    private final ListLoaderDelegate listDelegate = new ListLoaderDelegate(getViewState());

    @NonNull
    private final CompositeDisposableDelegate disposableDelegate = new CompositeDisposableDelegate();

    @Inject
    public WardropesPresenter(
            @Named("mode") @NonNull final ViewMode mode,
            @NonNull final WardrobesInteractor interactor,
            @NonNull final IdBus bus
    ) {
        this.bus = bus;
        this.interactor = interactor;
        this.mode = mode;
    }

    @Override
    protected void onFirstViewAttach() {
        loadMoreData(AppConstants.DEFAULT_ID);
    }

    @Override
    public void loadMoreData(long lastId) {
        Log.d(TAG, "loadMoreData");
        interactor
                .getWardrobes(lastId)
                .subscribe(
                        list -> {
                            getViewState().onLoadFinished(list);
                        },
                        e -> Log.e(TAG, e.getMessage())
                );
    }

    @Override
    public void onLongItemClick(IDbModel model) {
        if (mode == ViewMode.WARDROPE_MODE) {
            disposableDelegate.addToCompositeDisposable(
                    listDelegate.getDisposable(
                            interactor.deleteWardropes((Wardrope) model),
                            isDel -> getViewState().deleteListItem((Wardrope) model),
                            e -> Log.e(TAG, e.getMessage())
                    )
            );
        }
    }

    private void resolveClick(IDbModel model) {
        switch (mode) {
            case WARDROPE_MODE:
                getViewState().navigateToAddUpdateWardropeView(model.getId());
                break;
            case LOOK_MODE:
                bus.passData(new Pair<>(ViewMode.WARDROPE_MODE, model.getId()));
                getViewState().navigateToThingsFilteredByWardrope(model.getId());
                break;
        }
    }

    @Override
    public void addOrUpdateListItem(final long id) {
        disposableDelegate.addToCompositeDisposable(
                listDelegate.getDisposable(
                        interactor.getWardrobe(id),
                        item -> getViewState().invalidateListItem(item),
                        e -> Log.e(TAG, e.getMessage())
                )
        );
    }

    @Override
    public void onItemClick(IDbModel model) {
        resolveClick(model);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposableDelegate.unsubscribe();
    }
}
