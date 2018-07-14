package com.kirill.kochnev.homewardrobe.presentation.views;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kirill.kochnev.homewardrobe.db.models.Look;
import com.kirill.kochnev.homewardrobe.presentation.views.base.IPaginationView;

import java.util.HashSet;

/**
 * Created by kirill on 21.04.17.
 */

public interface ILooksView extends IPaginationView<Look> {

    void setEditMode(boolean isEditMode);

    void markAdapterViews(HashSet<Long> set);

    @StateStrategyType(SkipStrategy.class)
    void navigateToUpdateLookView(Long id);
}
