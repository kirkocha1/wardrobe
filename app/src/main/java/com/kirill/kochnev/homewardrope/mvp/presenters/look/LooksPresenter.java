package com.kirill.kochnev.homewardrope.mvp.presenters.look;

import android.util.Log;
import android.util.Pair;

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
import com.kirill.kochnev.homewardrope.utils.bus.IdBus;
import com.kirill.kochnev.homewardrope.utils.bus.StateBus;

import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kirill on 21.04.17.
 */

@InjectViewState
public class LooksPresenter extends BaseDbListPresenter<ILooksView> {

    public static final String TAG = "LooksPresenter";
    private ViewMode viewMode;
    private boolean isEdit;
    private long filterId;

    @Inject
    protected IdBus idBus;

    @Inject
    protected StateBus stateBus;

    @Inject
    ILooksInteractor interactor;

    public LooksPresenter(ViewMode mode, boolean isEdit, long filterId) {
        WardropeApplication.getLookComponent().inject(this);
        this.isEdit = isEdit;
        initMode(mode, filterId);
    }

    private void initMode(ViewMode mode, long wardropeId) {
        this.viewMode = mode;
        this.filterId = wardropeId;
        getViewState().setEditMode(isEdit);
        stateBus.register(statePair -> {
            updateModeState(statePair.second);
        });
    }

    private void updateModeState(boolean mode) {
        isEdit = mode;
        getViewState().setEditMode(isEdit);
        if (viewMode != ViewMode.LOOK_MODE) {
            if (isEdit) {
                unsubscribeOnDestroy(getDisposable(interactor.getLooksByWardrope(AppConstants.DEFAULT_ID, AppConstants.DEFAULT_ID),
                        list -> {
                            getViewState().onLoadFinished(list);
                            setIds(list);
                        },
                        e -> Log.e(TAG, e.getMessage())));
            } else {
                loadMoreData(AppConstants.DEFAULT_ID);
            }
        }
    }

    private void setIds(List<Look> list) {
        HashSet<Long> ids = new HashSet<>();
        for (Look look : list) {
            if (look.getWardropeId() != null && look.getWardropeId() == filterId) {
                ids.add(look.getId());
            }
        }
        getViewState().addIdsToAdapter(ids);
    }

    @Override
    public void loadMoreData(long lastId) {
        Log.d(TAG, "loadMoreData");
        unsubscribeOnDestroy(getDisposable(interactor.getLooksByWardrope(lastId, viewMode == ViewMode.WARDROPE_MODE && isEdit ?
                        AppConstants.DEFAULT_ID : filterId),
                list -> getViewState().onLoadFinished(list),
                e -> Log.e(TAG, e.getMessage())));
    }

    @Override
    public void onLongItemClick(IDbModel model) {
        if (viewMode == ViewMode.LOOK_MODE) {
            unsubscribeOnDestroy(interactor.deleteLook((Look) model)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(isDel -> {
                        getViewState().notifyListChanges((Look) model);
                    }));
        }
    }

    @Override
    public void onItemClick(IDbModel model) {
        if (viewMode != ViewMode.LOOK_MODE && isEdit) {
            idBus.passData(new Pair<>(ViewMode.LOOK_MODE, model.getId()));
        } else {
            getViewState().openUpdateActivity(UpdateLookActivity.createIntent(model.getId()));
        }

    }

    @Override
    public void addOrUpdateListItem(long id) {

    }

    @Override
    public void onDestroy() {
        WardropeApplication.clearLookComponent();
        super.onDestroy();
    }
}
