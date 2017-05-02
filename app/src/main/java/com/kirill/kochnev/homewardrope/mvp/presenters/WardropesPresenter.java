package com.kirill.kochnev.homewardrope.mvp.presenters;

import android.content.Intent;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseDbListPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IWardropeView;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractWardropeRepository;
import com.kirill.kochnev.homewardrope.ui.activities.AddUpdateWardropeActivity;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.kirill.kochnev.homewardrope.ui.activities.AddUpdateWardropeActivity.WARDROPE_ID;

/**
 * Created by kirill on 30.03.17.
 */
@InjectViewState
public class WardropesPresenter extends BaseDbListPresenter<IWardropeView> {
    private static final String TAG = "WardropesPresenter";

    @Inject
    protected AbstractWardropeRepository wardropes;

    private ViewMode mode;

    public WardropesPresenter(ViewMode mode) {
        WardropeApplication.getComponent().inject(this);
        this.mode = mode;
    }

    protected void refreshList() {
        unsubscribeOnDestroy(wardropes.query(AppConstants.DEFAULT_ID).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> getViewState().onLoadFinished(list), e -> e.printStackTrace()));
    }

    @Override
    public void loadMoreData(long lastId) {
        Log.d(TAG, "loadMoreData");
        unsubscribeOnDestroy(wardropes.query(lastId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> getViewState().onLoadFinished(list), e -> e.printStackTrace()));
    }

    @Override
    public void onLongItemClick(IDbModel model) {
        if (mode == ViewMode.WARDROPE_MODE) {
            unsubscribeOnDestroy(wardropes.deletItem((Wardrope) model)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(isDel -> {
                        getViewState().notifyListChanges((Wardrope) model);
                    }));
        }
    }

    private void resolveClick(IDbModel model) {
        switch (mode) {
            case WARDROPE_MODE:
                Intent intent = new Intent(WardropeApplication.getContext(), AddUpdateWardropeActivity.class);
                intent.putExtra(WARDROPE_ID, model.getId());
                getViewState().openUpdateActivity(intent);
                break;
            case LOOK_MODE:
                getViewState().setThingsByWardrope(model.getId());
                break;
        }
    }

    @Override
    public void onItemClick(IDbModel model) {
        resolveClick(model);
    }
}
