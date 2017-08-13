package com.kirill.kochnev.homewardrope.mvp.presenters.thing;

import android.util.Log;
import android.util.Pair;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.interactors.ThingsInteractor;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseDbListPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IThingsView;
import com.kirill.kochnev.homewardrope.utils.bus.IdBus;
import com.kirill.kochnev.homewardrope.utils.bus.StateBus;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kirill Kochnev on 25.02.17.
 */

@InjectViewState
public class ThingsPresenter extends BaseDbListPresenter<IThingsView> {

    public static final String TAG = "ThingsPresenter";
    public static final String THINGS_ID = "things_id";

    private long filterId = AppConstants.DEFAULT_ID;
    private boolean isEdit;
    private ViewMode viewMode;

    @Inject
    IdBus idBus;

    @Inject
    StateBus stateBus;

    @Inject
    ThingsInteractor interactor;

    public ThingsPresenter(ViewMode mode, boolean isEdit, long filterId) {
        WardropeApplication.getComponent().inject(this);
        this.isEdit = isEdit;
        initMode(mode, filterId);
    }

    private void initMode(ViewMode mode, long wardropeId) {
        viewMode = mode;
        this.filterId = wardropeId;
        getViewState().setEditMode(isEdit);
        stateBus.register(statePair -> {
            updateModeState(statePair.second);
        });
    }

    public void updateModeState(boolean mode) {
        isEdit = mode;
        getViewState().setEditMode(isEdit);
        if (viewMode != ViewMode.THING_MODE) {
            if (isEdit) {
                interactor.getWardropeThingIds(filterId).subscribe(set -> getViewState().addThingIdsToAdapter(set));
                unsubscribeOnDestroy(getListDisposable(interactor.getThingsByWardrope(AppConstants.DEFAULT_ID, AppConstants.DEFAULT_ID)));
            } else {
                loadMoreData(AppConstants.DEFAULT_ID);
            }
        }
    }

    @Override
    public void loadMoreData(long lastId) {
        Log.d(TAG, "loadMoreData");
        unsubscribeOnDestroy(getDisposable(interactor.getThingsByWardrope(lastId, viewMode == ViewMode.WARDROPE_MODE && isEdit ?
                        AppConstants.DEFAULT_ID : filterId),
                list -> getViewState().onLoadFinished(list),
                e -> Log.e(TAG, "refreshList: " + e.getMessage())));
    }

    @Override
    public void onLongItemClick(IDbModel model) {
        if (viewMode == ViewMode.THING_MODE) {
            unsubscribeOnDestroy(interactor.deleteThings((Thing) model)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(result -> getViewState().deleteListView((Thing) model)));
        }
    }

    @Override
    public void onItemClick(IDbModel model) {
        resolveClick((Thing) model);
    }


    private void resolveClick(Thing thing) {
        if (viewMode != ViewMode.THING_MODE && isEdit) {
            idBus.passData(new Pair<>(ViewMode.THING_MODE, thing.getId()));
        } else {
            getViewState().navigateToAddUpdateThingView(false, thing.getId());
        }
    }

    @Override
    public void addOrUpdateListItem(long id) {
        unsubscribeOnDestroy(getDisposable(interactor.getThing(id),
                item -> getViewState().invalidateItemView(item),
                e -> Log.e(TAG, e.getMessage())));
    }
}
