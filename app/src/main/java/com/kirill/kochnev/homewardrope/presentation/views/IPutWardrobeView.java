package com.kirill.kochnev.homewardrope.presentation.views;

import android.content.Intent;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kirill.kochnev.homewardrope.db.models.Wardrobe;

/**
 * Created by kirill on 30.03.17.
 */
@StateStrategyType(SkipStrategy.class)
public interface IPutWardrobeView extends MvpView {

    void setCount(int thingsCount, int looksCount);

    void initView(Wardrobe wardrobe);

    void onSave(Intent intent);

    void changeBtnsMode(boolean mode);

}
