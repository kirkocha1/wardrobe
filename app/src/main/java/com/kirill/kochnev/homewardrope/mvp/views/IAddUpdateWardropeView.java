package com.kirill.kochnev.homewardrope.mvp.views;

import android.content.Intent;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;

/**
 * Created by kirill on 30.03.17.
 */
@StateStrategyType(SkipStrategy.class)
public interface IAddUpdateWardropeView extends MvpView {

    void setCount(int thingsCount, int looksCount);

    void initView(Wardrope wardrope);

    void onSave(Intent intent);

    void changeBtnsMode(boolean mode);

}
