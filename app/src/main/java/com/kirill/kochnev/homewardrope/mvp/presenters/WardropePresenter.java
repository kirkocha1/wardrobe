package com.kirill.kochnev.homewardrope.mvp.presenters;

import android.content.Intent;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseDbListPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IWardropeView;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractWardropeRepository;
import com.kirill.kochnev.homewardrope.ui.activities.AddUpdateThingActivity;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kirill on 30.03.17.
 */
@InjectViewState
public class WardropePresenter extends BaseDbListPresenter<IWardropeView> {
    public static final String TAG = "WardropePresenter";
    public static final String WARDROPES_ID = "wardropes_id";

    @Inject
    protected AbstractWardropeRepository wardropes;

    public WardropePresenter() {
        WardropeApplication.getComponent().inject(this);
    }

    public void refreshList() {
        unsubscribeOnDestroy(wardropes.getNextList(-1).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> getViewState().initList(list), e -> e.printStackTrace()));
    }

    @Override
    public void attachView(IWardropeView view) {
        super.attachView(view);
        refreshList();
    }

    @Override
    public void loadMoreData(long id) {
        Log.d(TAG, "loadMoreData");
        unsubscribeOnDestroy(wardropes.getNextList(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> getViewState().onLoadFinished(list), e -> e.printStackTrace()));
    }

    @Override
    public void onLongItemClick(IDbModel model) {
        unsubscribeOnDestroy(wardropes.deletItem((Wardrope) model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isDel -> {
                    getViewState().notifyListChanges((Wardrope) model);
                }));

    }

    @Override
    public void onItemClick(IDbModel model) {
        Intent intent = new Intent(WardropeApplication.getContext(), AddUpdateThingActivity.class);
        intent.putExtra(WARDROPES_ID, ((Thing) model).getId());
        getViewState().openUpdateActivity(intent);
    }
}
