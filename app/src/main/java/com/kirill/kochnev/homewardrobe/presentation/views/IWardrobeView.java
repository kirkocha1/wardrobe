package com.kirill.kochnev.homewardrobe.presentation.views;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kirill.kochnev.homewardrobe.db.models.Wardrobe;
import com.kirill.kochnev.homewardrobe.presentation.views.base.IPaginationView;

/**
 * Created by kirill on 30.03.17.
 */

public interface IWardrobeView extends IPaginationView<Wardrobe> {

    @StateStrategyType(SkipStrategy.class)
    void navigateToThingsFilteredByWardrope(long id);

    @StateStrategyType(SkipStrategy.class)
    void navigateToAddUpdateWardropeView(Long id);
}

