package com.kirill.kochnev.homewardrope.mvp.presenters.thing;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.interactors.interfaces.IThingInteractor;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseDbListPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IThingsView;
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
    protected IThingInteractor interactor;

    public ThingsPresenter(ViewMode mode, boolean isEdit, long filterId) {
        WardropeApplication.getComponent().inject(this);
        this.isEdit = isEdit;
        initMode(mode, filterId);
    }

    private void initMode(ViewMode mode, long wardropeId) {
        viewMode = mode;
        this.filterId = wardropeId;
        getViewState().setEditMode(isEdit);
    }

    public void refreshList() {
        unsubscribeOnDestroy(getListDisposable(interactor.getThingsByWardrope(AppConstants.DEFAULT_ID, filterId)));
    }

    public void updateModeState(boolean mode) {
        isEdit = mode;
        getViewState().setEditMode(isEdit);
        if (viewMode != ViewMode.THING_MODE) {
            if (isEdit) {
                interactor.getWardropeThingIds(filterId).subscribe(set -> getViewState().addThingIdsToAdapter(set));
                unsubscribeOnDestroy(getListDisposable(interactor.getThingsByWardrope(AppConstants.DEFAULT_ID, AppConstants.DEFAULT_ID)));
            } else {
                refreshList();
            }
        }
    }

    @Override
    public void loadMoreData(long lastId) {
        Log.d(TAG, "loadMoreData");
        unsubscribeOnDestroy(getListDisposable(interactor.getThingsByWardrope(lastId, filterId)));
    }

    @Override
    public void onLongItemClick(IDbModel model) {
        if (viewMode == ViewMode.THING_MODE) {
            unsubscribeOnDestroy(interactor.deleteThings((Thing) model)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(isDel -> {
                        getViewState().notifyListChanges((Thing) model);
                    }));
        }
    }

    @Override
    public void onItemClick(IDbModel model) {
        resolveClick((Thing) model);
    }


    private void resolveClick(Thing thing) {
        switch (viewMode) {
            case WARDROPE_MODE:
                if (isEdit) {
                    getViewState().addThingId(thing.getId());
                } else {
                    getViewState().openUpdateActivity(AddUpdateThingActivity.createIntent(thing.getId(), false));
                }
                break;
            case LOOK_MODE:
                if (isEdit) {
                    getViewState().addThingId(thing.getId());
                }
                break;
            default:
                getViewState().openUpdateActivity(AddUpdateThingActivity.createIntent(thing.getId(), false));
        }

    }

    private Disposable getListDisposable(Single<List<Thing>> obsevable) {
        return obsevable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> getViewState().onLoadFinished(list), e -> Log.e(TAG, "refreshList: " + e.getMessage()));
    }

}
