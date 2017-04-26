package com.kirill.kochnev.homewardrope.mvp.presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseDbListPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.ILooksView;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractLookRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kirill on 21.04.17.
 */

@InjectViewState
public class LooksPresenter extends BaseDbListPresenter<ILooksView> {

    public static final String TAG = "LooksPresenter";

    @Inject
    protected AbstractLookRepository looks;

    public LooksPresenter() {
        WardropeApplication.getComponent().inject(this);
    }

    @Override
    public void loadMoreData(long lastId) {
        Log.d(TAG, "loadMoreData");
        unsubscribeOnDestroy(looks.query(lastId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> getViewState().onLoadFinished(list), e -> e.printStackTrace()));
    }

    @Override
    protected void refreshList() {
        unsubscribeOnDestroy(looks.query(AppConstants.DEFAULT_ID).subscribe(list -> getViewState().onLoadFinished(list),
                e -> e.printStackTrace()));
    }

    @Override
    public void onLongItemClick(IDbModel model) {

    }

    @Override
    public void onItemClick(IDbModel model) {

    }

}
