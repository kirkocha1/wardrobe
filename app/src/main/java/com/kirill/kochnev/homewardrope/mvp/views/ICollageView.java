package com.kirill.kochnev.homewardrope.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.enums.CollageMode;

import java.util.List;

/**
 * Created by kirill on 27.04.17.
 */

@StateStrategyType(SkipStrategy.class)
public interface ICollageView extends MvpView {

    void constructView(List<Thing> things, CollageMode mode);

}
