package com.kirill.kochnev.homewardrope.mvp.views;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.mvp.views.base.IPaginationView;

import java.util.HashSet;

/**
 * Created by kirill on 21.04.17.
 */

public interface ILooksView extends IPaginationView<Look> {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setEditMode(boolean isEditMode);

    void addIdsToAdapter(HashSet<Long> set);

}
