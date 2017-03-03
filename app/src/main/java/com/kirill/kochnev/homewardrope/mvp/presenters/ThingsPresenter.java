package com.kirill.kochnev.homewardrope.mvp.presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.ThingDao;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IThingsView;

import javax.inject.Inject;

/**
 * Created by Kirill Kochnev on 25.02.17.
 */

@InjectViewState
public class ThingsPresenter extends BaseDbListPresenter<IThingsView> {

    public static final String TAG = "ThingsPresenter";
    public static final int LIMIT = 20;
    @Inject
    protected ThingDao things;

    public ThingsPresenter() {
        WardropeApplication.getComponent().inject(this);
        getViewState().initList(things.queryBuilder().limit(LIMIT).list());
    }

    @Override
    public void loadMoreData(long id) {
        Log.d(TAG, "loadMoreData");
        getViewState().onLoadFinished(things.queryBuilder().where(ThingDao.Properties.Id.gt(id)).limit(LIMIT).list());
    }
}
