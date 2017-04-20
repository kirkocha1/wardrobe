package com.kirill.kochnev.homewardrope.mvp.presenters;

import android.content.Intent;
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
import com.kirill.kochnev.homewardrope.ui.activities.AddUpdateThingActivity;

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

    private boolean isWardropeMode = false;
    private long wardropeId;

    @Inject
    protected AbstractThingRepository things;

    public ThingsPresenter(ViewMode mode, long wardropeId) {
        WardropeApplication.getComponent().inject(this);
        initMode(mode, wardropeId);
    }

    private void initMode(ViewMode mode, long wardropeId) {
        switch (mode) {
            case WARDROPE_MODE:
                isWardropeMode = true;
                this.wardropeId = wardropeId;
                break;
        }
    }

    public void refreshList() {
        unsubscribeOnDestroy(things.getNextList(AppConstants.DEFAULT_ID).subscribe(list -> getViewState().onLoadFinished(list),
                e -> e.printStackTrace()));
        if (isWardropeMode) {
            things.getWardropeThingIds(wardropeId).subscribe(set -> {
                getViewState().addThingIdsToAdapter(set);
            });
        }
    }

    @Override
    public void loadMoreData(long id) {
        Log.d(TAG, "loadMoreData");
        unsubscribeOnDestroy(things.getNextList(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> getViewState().onLoadFinished(list), e -> e.printStackTrace()));
    }

    @Override
    public void onLongItemClick(IDbModel model) {
        if (!isWardropeMode) {
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
        if (!isWardropeMode) {
            Intent intent = new Intent(WardropeApplication.getContext(), AddUpdateThingActivity.class);
            intent.putExtra(THINGS_ID, model.getId());
            getViewState().openUpdateActivity(intent);
        } else {
            getViewState().addThingId(thing.getId());
        }

    }
}
