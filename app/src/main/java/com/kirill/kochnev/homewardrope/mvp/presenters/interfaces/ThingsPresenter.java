package com.kirill.kochnev.homewardrope.mvp.presenters.interfaces;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.ThingDao;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IThingsView;

import javax.inject.Inject;

/**
 * Created by Kirill Kochnev on 25.02.17.
 */

@InjectViewState
public class ThingsPresenter extends MvpPresenter<IThingsView> {

    @Inject
    protected ThingDao things;

    public ThingsPresenter() {
        WardropeApplication.getComponent().inject(this);
        getViewState().initList(things.queryBuilder().listLazy());
    }
}
