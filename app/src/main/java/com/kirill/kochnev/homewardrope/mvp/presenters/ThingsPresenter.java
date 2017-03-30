package com.kirill.kochnev.homewardrope.mvp.presenters;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.db.models.ThingDao;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IThingsView;

import javax.inject.Inject;

/**
 * Created by Kirill Kochnev on 25.02.17.
 */

@InjectViewState
public class ThingsPresenter extends BaseDbListPresenter<IThingsView> {

    public static final String TAG = "ThingsPresenter";
    public static final int LIMIT = 10;
    public static final String THINGS_ID = "things_id";

    @Inject
    protected ThingDao things;

    public ThingsPresenter() {
        WardropeApplication.getComponent().inject(this);
    }

    public void refreshList() {
        getViewState().initList(things.queryBuilder().limit(LIMIT).list());
    }

    @Override
    public void attachView(IThingsView view) {
        super.attachView(view);
        refreshList();
    }

    @Override
    public void loadMoreData(long id) {
        Log.d(TAG, "loadMoreData");
        getViewState().onLoadFinished(things.queryBuilder().where(ThingDao.Properties.Id.gt(id)).limit(LIMIT).list());
    }

    @Override
    public void onLongItemClick(IDbModel model) {
        Toast.makeText(WardropeApplication.getContext(), "LONG CLICK", Toast.LENGTH_SHORT).show();
        things.delete((Thing) model);
        getViewState().notifyListChanges((Thing) model);
    }


    @Override
    public void onItemClick(IDbModel model) {
        Bundle bundle = new Bundle();
        bundle.putLong(THINGS_ID, ((Thing) model).getId());
        getViewState().openUpdateActivity(bundle);
    }
}
