package com.kirill.kochnev.homewardrope.mvp.presenters.look;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.interactors.interfaces.ILooksInteractor;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseDbListPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.ILooksView;
import com.kirill.kochnev.homewardrope.ui.activities.look.UpdateLookActivity;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kirill on 21.04.17.
 */

@InjectViewState
public class LooksPresenter extends BaseDbListPresenter<ILooksView> {

    public static final String TAG = "LooksPresenter";
    private ViewMode mode;
    private boolean isEdit;
    private long wardropeId;

    @Inject
    ILooksInteractor interactor;

    public LooksPresenter(ViewMode mode, boolean isEdit, long wardropeId) {
        WardropeApplication.getLookComponent().inject(this);
        this.mode = mode;
        this.isEdit = isEdit;
        this.wardropeId = wardropeId;
    }

    @Override
    public void loadMoreData(long lastId) {
        Log.d(TAG, "loadMoreData");
        unsubscribeOnDestroy(interactor.getLooks(lastId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> getViewState().onLoadFinished(list),
                        e -> Log.e(TAG, e.getMessage())));
    }

    @Override
    protected void refreshList() {
        unsubscribeOnDestroy(interactor.getLooks(AppConstants.DEFAULT_ID)
                .subscribe(list -> getViewState().onLoadFinished(list),
                        e -> Log.e(TAG, e.getMessage())));
    }

    @Override
    public void onLongItemClick(IDbModel model) {
        unsubscribeOnDestroy(interactor.deleteLook((Look) model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isDel -> {
                    getViewState().notifyListChanges((Look) model);
                }));
    }

    @Override
    public void onItemClick(IDbModel model) {
        getViewState().openUpdateActivity(UpdateLookActivity.createIntent(model.getId()));
    }

    @Override
    public void onDestroy() {
        WardropeApplication.clearLookComponent();
        super.onDestroy();
    }
}
