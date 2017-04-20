package com.kirill.kochnev.homewardrope.mvp.presenters.base;

import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.mvp.views.base.IPaginationView;

/**
 * Created by Kirill Kochnev on 12.02.17.
 */

public abstract class BaseDbListPresenter<V extends IPaginationView> extends BaseMvpPresenter<V> {
    public abstract void loadMoreData(long modelId);

    public abstract void onLongItemClick(IDbModel model);

    public abstract void onItemClick(IDbModel model);

    protected abstract void refreshList();

    private void dropList() {
        getViewState().dropData();
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);
        refreshList();
    }

    @Override
    public void detachView(V view) {
        dropList();
        super.detachView(view);

    }


}
