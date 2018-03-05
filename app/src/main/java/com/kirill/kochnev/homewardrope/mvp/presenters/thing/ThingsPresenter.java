package com.kirill.kochnev.homewardrope.mvp.presenters.thing;

import android.support.annotation.NonNull;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.interactors.ThingsInteractor;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.CompositeDisposableDelegate;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.IPaginator;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.ListLoaderDelegate;
import com.kirill.kochnev.homewardrope.mvp.views.IThingsView;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kirill Kochnev on 25.02.17.
 */

@InjectViewState
public class ThingsPresenter extends MvpPresenter<IThingsView> implements IPaginator {

    public static final String TAG = "ThingsPresenter";
    public static final String THINGS_ID = "things_id";

    private long filterId = AppConstants.DEFAULT_ID;
    private boolean isEdit;
    private ViewMode viewMode;
    private @NonNull ThingsInteractor interactor;

    @NonNull
    private final CompositeDisposableDelegate disposableDelegate = new CompositeDisposableDelegate();

    @NonNull
    private final ListLoaderDelegate listDelegate = new ListLoaderDelegate(getViewState());

    @Inject
    public ThingsPresenter(@Named("filterId") long filterId,
                           @Named("isEdit") boolean isEdit,
                           @Named("mode") ViewMode mode,
                           @NonNull ThingsInteractor interactor
    ) {
        this.interactor = interactor;
        this.isEdit = isEdit;
        initMode(mode, filterId);
    }

    @Override
    protected void onFirstViewAttach() {
        loadMoreData(AppConstants.DEFAULT_ID);
    }

    private void initMode(final ViewMode mode, final long wardropeId) {
        viewMode = mode;
        filterId = wardropeId;
        disposableDelegate.addToCompositeDisposable(
                interactor
                        .observeEditableModeChanges()
                        .subscribe(
                                state -> updateModeState(state.second),
                                e -> Log.e(TAG, e.getMessage())
                        )
        );
        getViewState().setEditMode(isEdit);

    }

    private void updateModeState(final boolean mode) {
        isEdit = mode;
        getViewState().setEditMode(isEdit);
        if (viewMode != ViewMode.THING_MODE) {
            if (isEdit) {
                disposableDelegate.addToCompositeDisposable(
                        interactor
                                .getWardropeThingIds(filterId)
                                .subscribe(set -> getViewState().addThingIdsToAdapter(set))
                );
                disposableDelegate.addToCompositeDisposable(
                        listDelegate.getListDisposable(
                                interactor.getThingsByWardrope(AppConstants.DEFAULT_ID, AppConstants.DEFAULT_ID))
                );
            } else {
                loadMoreData(AppConstants.DEFAULT_ID);
            }
        }
    }

    @Override
    public void loadMoreData(final long lastId) {
        Log.d(TAG, "loadMoreData");
        disposableDelegate.addToCompositeDisposable(
                listDelegate.getDisposable(
                        interactor
                                .getThingsByWardrope(
                                        lastId, viewMode == ViewMode.WARDROBE_MODE && isEdit ?
                                                AppConstants.DEFAULT_ID : filterId
                                ),
                        list -> getViewState().onLoadFinished(list),
                        e -> Log.e(TAG, "refreshList: " + e.getMessage())
                )
        );
    }

    @Override
    public void onLongItemClick(final IDbModel model) {
        if (viewMode == ViewMode.THING_MODE) {
            disposableDelegate.addToCompositeDisposable(
                    interactor
                            .deleteThings((Thing) model)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(result -> getViewState().deleteListItem((Thing) model))
            );
        }
    }

    @Override
    public void onItemClick(final IDbModel model) {
        resolveClick((Thing) model);
    }


    private void resolveClick(final Thing thing) {
        if (viewMode != ViewMode.THING_MODE && isEdit) {
            disposableDelegate.addToCompositeDisposable(
                    interactor
                            .sendThingIdWithMode(ViewMode.THING_MODE, thing.getId())
                            .subscribe(
                                    () -> {
                                    },
                                    e -> Log.e(TAG, e.getMessage())
                            )
            );
        } else {
            getViewState().navigateToAddUpdateThingView(false, thing.getId());
        }
    }

    @Override
    public void addOrUpdateListItem(final long id) {
        disposableDelegate.addToCompositeDisposable(
                listDelegate.getDisposable(
                        interactor.getThing(id),
                        item -> getViewState().invalidateListItem(item),
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
