package com.kirill.kochnev.homewardrope.mvp.presenters;

import android.content.Intent;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseDbListPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IThingsView;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractThingRepository;
import com.kirill.kochnev.homewardrope.ui.activities.AddUpdateThingActivity;

import java.util.HashSet;

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

    @Inject
    protected AbstractThingRepository things;

    public ThingsPresenter(int mode) {
        WardropeApplication.getComponent().inject(this);
        initMode(mode);
    }

    private void initMode(int mode) {
        switch (mode) {
            case 1:
                isWardropeMode = true;
                break;
            case -1:
                break;
        }
    }

    public void refreshList() {
        unsubscribeOnDestroy(things.getNextList(-1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> getViewState().initList(list, isWardropeMode), e -> e.printStackTrace()));
    }

    @Override
    public void attachView(IThingsView view) {
        super.attachView(view);
        refreshList();
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
            intent.putExtra(THINGS_ID, ((Thing) model).getId());
            getViewState().openUpdateActivity(intent);
        } else {
            getViewState().addThingId(thing.getId());
        }

    }
}
