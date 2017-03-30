package com.kirill.kochnev.homewardrope.mvp.presenters.base;

import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseMvpPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IPaginationView;

/**
 * Created by Kirill Kochnev on 12.02.17.
 */

public abstract class BaseDbListPresenter<V extends IPaginationView> extends BaseMvpPresenter<V> {
    public abstract void loadMoreData(long modelId);
    public abstract void onLongItemClick(IDbModel model);
    public abstract void onItemClick(IDbModel model);
}
