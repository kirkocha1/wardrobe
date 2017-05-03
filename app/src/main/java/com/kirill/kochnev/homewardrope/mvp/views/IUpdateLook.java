package com.kirill.kochnev.homewardrope.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kirill.kochnev.homewardrope.db.models.Look;

/**
 * Created by kirill on 03.05.17.
 */
@StateStrategyType(SkipStrategy.class)
public interface IUpdateLook extends MvpView {

    void setLookData(Look look);

    void onSave();

}
