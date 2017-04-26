package com.kirill.kochnev.homewardrope.mvp.presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseDbListPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IThingsView;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractThingRepository;
import com.kirill.kochnev.homewardrope.repositories.utils.ThingsByWardropeSpecification;
import com.kirill.kochnev.homewardrope.ui.activities.AddUpdateThingActivity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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
    protected AbstractThingRepository things;

    public ThingsPresenter(ViewMode mode, boolean isEdit, long filterId) {
        WardropeApplication.getComponent().inject(this);
        this.isEdit = isEdit;
        initMode(mode, filterId);
    }

    private void initMode(ViewMode mode, long wardropeId) {
        viewMode = mode;
        this.filterId = wardropeId;
    }

    private Single<List<Thing>> resolveObservable(long lastId) {
        Single<List<Thing>> observable;
        observable = things.query(lastId);
        switch (viewMode) {
            case WARDROPE_MODE:
                if (isEdit) {
                    things.getWardropeThingIds(filterId).subscribe(set -> getViewState().addThingIdsToAdapter(set));
                } else {
                    observable = things.query(new ThingsByWardropeSpecification(lastId, filterId));
                }
                break;
        }
        return observable;
    }

    private Disposable getListDisposable(Single<List<Thing>> obsevable) {
        return obsevable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> getViewState().onLoadFinished(list), e -> Log.e(TAG, "refreshList: " + e.getMessage()));
    }

    public void refreshList() {
        unsubscribeOnDestroy(getListDisposable(resolveObservable(AppConstants.DEFAULT_ID)));
    }

    public void updateModeState(boolean mode) {
        isEdit = mode;
        refreshList();
    }

    @Override
    public void loadMoreData(long lastId) {
        Log.d(TAG, "loadMoreData");
        unsubscribeOnDestroy(getListDisposable(resolveObservable(lastId)));
    }

    @Override
    public void onLongItemClick(IDbModel model) {
        if (viewMode != ViewMode.WARDROPE_MODE) {
            unsubscribeOnDestroy(things.deletItem((Thing) model)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(isDel -> {
                        getViewState().notifyListChanges((Thing) model);
                    }));
        }
    }

    @Override
    public void onItemClick(IDbModel model) {
        Thing thing = (Thing) model;
        if (viewMode != ViewMode.WARDROPE_MODE) {
            getViewState().openUpdateActivity(AddUpdateThingActivity.createIntent(model.getId(), true));
        } else {
            if (isEdit) {
                getViewState().addThingId(thing.getId());
            } else {
                getViewState().openUpdateActivity(AddUpdateThingActivity.createIntent(model.getId(), false));
            }
        }
    }
}
