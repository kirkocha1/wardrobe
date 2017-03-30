package com.kirill.kochnev.homewardrope.mvp.views.interfaces;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.db.models.Thing;

import java.util.List;

/**
 * Created by Kirill Kochnev on 26.02.17.
 */

public interface IPaginationView<M extends IDbModel> extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void initList(List<M> models);

    @StateStrategyType(SkipStrategy.class)
    void onLoadFinished(List<M> data);

    @StateStrategyType(SkipStrategy.class)
    void notifyListChanges(M model);
}
