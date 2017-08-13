package com.kirill.kochnev.homewardrope.mvp.views;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.mvp.views.base.IPaginationView;

import java.util.HashSet;

/**
 * Created by kirill on 21.04.17.
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface ILooksView extends IPaginationView<Look> {

    void setEditMode(boolean isEditMode);

    void markAdapterViews(HashSet<Long> set);

    @StateStrategyType(SkipStrategy.class)
    void navigateToUpdateLookView(Long id);
}
