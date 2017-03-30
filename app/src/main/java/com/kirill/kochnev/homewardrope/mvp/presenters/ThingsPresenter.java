package com.kirill.kochnev.homewardrope.mvp.presenters;

import android.os.Bundle;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IThingsView;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractThingRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kirill Kochnev on 25.02.17.
 */

@InjectViewState
public class ThingsPresenter extends BaseDbListPresenter<IThingsView> {

    public static final String TAG = "ThingsPresenter";
    public static final int LIMIT = 10;
    public static final String THINGS_ID = "things_id";

    @Inject
    protected AbstractThingRepository things;

    public ThingsPresenter() {
        WardropeApplication.getComponent().inject(this);
    }

    public void refreshList() {
        things.getNextList(-1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> getViewState().initList(list), e -> e.printStackTrace());
    }

    @Override
    public void attachView(IThingsView view) {
        super.attachView(view);
        refreshList();
    }

    @Override
    public void loadMoreData(long id) {
        Log.d(TAG, "loadMoreData");
        things.getNextList(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> getViewState().onLoadFinished(list), e -> e.printStackTrace());
    }

    @Override
    public void onLongItemClick(IDbModel model) {
        things.deletItem((Thing) model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isDel -> {
                    getViewState().notifyListChanges((Thing) model);
                });

    }

    @Override
    public void onItemClick(IDbModel model) {
        Bundle bundle = new Bundle();
        bundle.putLong(THINGS_ID, ((Thing) model).getId());
        getViewState().openUpdateActivity(bundle);
    }
}
