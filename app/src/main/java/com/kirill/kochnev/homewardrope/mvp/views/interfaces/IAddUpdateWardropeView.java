package com.kirill.kochnev.homewardrope.mvp.views.interfaces;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;

/**
 * Created by kirill on 30.03.17.
 */

public interface IAddUpdateWardropeView extends MvpView {

    void setCount(int count);

    void addThingId(long id);

    void initView(Wardrope wardrope);

    @StateStrategyType(SkipStrategy.class)
    void onSave();

}
