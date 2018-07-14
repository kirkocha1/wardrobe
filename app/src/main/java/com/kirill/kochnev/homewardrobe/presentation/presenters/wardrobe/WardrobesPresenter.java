package com.kirill.kochnev.homewardrobe.presentation.presenters.wardrobe;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kirill.kochnev.homewardrobe.AppConstants;
import com.kirill.kochnev.homewardrobe.WardrobeApplication;
import com.kirill.kochnev.homewardrobe.db.models.IDbModel;
import com.kirill.kochnev.homewardrobe.db.models.Wardrobe;
import com.kirill.kochnev.homewardrobe.enums.ViewMode;
import com.kirill.kochnev.homewardrobe.interactors.WardrobesInteractor;
import com.kirill.kochnev.homewardrobe.presentation.presenters.base.CompositeDisposableDelegate;
import com.kirill.kochnev.homewardrobe.presentation.presenters.base.IPaginator;
import com.kirill.kochnev.homewardrobe.presentation.presenters.base.ListLoaderDelegate;
import com.kirill.kochnev.homewardrobe.presentation.views.IWardrobeView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by kirill on 30.03.17.
 */
@InjectViewState
public class WardrobesPresenter extends MvpPresenter<IWardrobeView> implements IPaginator<Wardrobe> {
    private static final String TAG = "WardrobesPresenter";

    @NonNull
    private WardrobesInteractor interactor;

    @NonNull
    private final ViewMode mode;

    @NonNull
    private final ListLoaderDelegate listDelegate = new ListLoaderDelegate(getViewState());

    @NonNull
    private final CompositeDisposableDelegate disposableDelegate = new CompositeDisposableDelegate();

    @Inject
    WardrobesPresenter(
            @Named("mode") @NonNull final ViewMode mode,
            @NonNull final WardrobesInteractor interactor
    ) {
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
        disposableDelegate.addToCompositeDisposable(interactor
                .getWardrobes(lastId)
                .subscribe(
                        list -> {
                            getViewState().onLoadFinished(list);
                        },
                        e -> Log.e(TAG, e.getMessage())
                )
        );
    }

    @Override
    public void onLongItemClick(Wardrobe model) {
        if (mode == ViewMode.WARDROBE_MODE) {
            disposableDelegate.addToCompositeDisposable(
                    listDelegate.getDisposable(
                            interactor.deleteWardropes((Wardrobe) model),
                            isDel -> getViewState().deleteListItem((Wardrobe) model),
                            e -> Log.e(TAG, e.getMessage())
                    )
            );
        }
    }

    @Override
    public void onItemClick(Wardrobe model) {
        resolveClick(model);
    }

    private void resolveClick(IDbModel model) {
        switch (mode) {
            case WARDROBE_MODE:
                getViewState().navigateToAddUpdateWardropeView(model.getId());
                break;
            case LOOK_MODE:
                interactor
                        .passClickedWardrobeData(new Pair<>(ViewMode.WARDROBE_MODE, model.getId()))
                        .subscribe(
                                () -> getViewState().navigateToThingsFilteredByWardrope(model.getId()),
                                e -> Log.e(TAG, e.getMessage())
                        );

                break;
        }
    }

    @Override
    public void putListItem(final long id) {
        disposableDelegate.addToCompositeDisposable(
                listDelegate.getDisposable(
                        interactor.getWardrobe(id),
                        item -> getViewState().invalidateListItem(item),
                        e -> Log.e(TAG, e.getMessage())
                )
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposableDelegate.unsubscribe();
        WardrobeApplication.getComponentHolder().clearWardrobeComponent();
    }
}
