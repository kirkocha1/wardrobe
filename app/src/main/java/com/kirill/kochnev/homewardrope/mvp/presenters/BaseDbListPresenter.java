package com.kirill.kochnev.homewardrope.mvp.presenters;

import com.arellomobile.mvp.MvpPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IPaginationView;

/**
 * Created by Kirill Kochnev on 12.02.17.
 */

public abstract class BaseDbListPresenter<V extends IPaginationView> extends MvpPresenter<V>{
    public abstract void loadMoreData(long modelId);
}
