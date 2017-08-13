package com.kirill.kochnev.homewardrope.mvp.views.base;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

/**
 * Created by Kirill Kochnev on 26.02.17.
 */
public interface IPaginationView<M> extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void onLoadFinished(List<M> data);

    @StateStrategyType(SkipStrategy.class)
    void deleteListView(M model);

    @StateStrategyType(SkipStrategy.class)
    void invalidateItemView(M model);
}
