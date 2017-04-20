package com.kirill.kochnev.homewardrope.mvp.views.base;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

/**
 * Created by Kirill Kochnev on 26.02.17.
 */
@StateStrategyType(SkipStrategy.class)
public interface IPaginationView<M> extends MvpView {

    void dropData();

    void onLoadFinished(List<M> data);

    void notifyListChanges(M model);
}
