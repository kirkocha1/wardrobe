package com.kirill.kochnev.homewardrope.mvp.presenters.wardrope;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.interactors.WardropesInteractor;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseMvpPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.IPaginator;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.ListLoaderDelegate;
import com.kirill.kochnev.homewardrope.mvp.views.IWardropeView;
import com.kirill.kochnev.homewardrope.utils.bus.IdBus;

import javax.inject.Inject;

/**
 * Created by kirill on 30.03.17.
 */
@InjectViewState
public class WardropesPresenter extends BaseMvpPresenter<IWardropeView> implements IPaginator {
    private static final String TAG = "WardropesPresenter";

    @Inject
    IdBus bus;

    @Inject
    WardropesInteractor interactor;

    @NonNull
    private final ListLoaderDelegate listDelegate = new ListLoaderDelegate(getViewState());

    @NonNull
    private final ViewMode mode;

    public WardropesPresenter(@NonNull final ViewMode mode) {
        WardropeApplication.getComponent().inject(this);
        this.mode = mode;
        loadMoreData(AppConstants.DEFAULT_ID);
    }

//    @Override
//    protected void onFirstViewAttach() {
//
//    }

    @Override
    public void loadMoreData(long lastId) {
        Log.d(TAG, "loadMoreData");
        interactor
                .getWardropes(lastId)
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
            unsubscribeOnDestroy(listDelegate.getDisposable(interactor.deleteWardropes((Wardrope) model),
                    isDel -> getViewState().deleteListItem((Wardrope) model), e -> Log.e(TAG, e.getMessage())));
        }
    }

    private void resolveClick(IDbModel model) {
        switch (mode) {
            case WARDROPE_MODE:
                getViewState().navigateToAddUpdateWardropeView(model.getId());
                break;
            case LOOK_MODE:
                bus.passData(new Pair<>(ViewMode.WARDROPE_MODE, model.getId()));
                getViewState().setThingsByWardrope(model.getId());
                break;
        }
    }

    @Override
    public void addOrUpdateListItem(final long id) {
        unsubscribeOnDestroy(listDelegate.getDisposable(interactor.getWardrope(id),
                item -> getViewState().invalidateListItem(item),
                e -> Log.e(TAG, e.getMessage())));
    }

    @Override
    public void onItemClick(IDbModel model) {
        resolveClick(model);
    }
}
