package com.kirill.kochnev.homewardrope.mvp.views;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.mvp.views.base.IPaginationView;

/**
 * Created by kirill on 30.03.17.
 */

public interface IWardropeView extends IPaginationView<Wardrope> {

    void setThingsByWardrope(long id);

    @StateStrategyType(SkipStrategy.class)
    void navigateToAddUpdateWardropeView(Long id);
}

