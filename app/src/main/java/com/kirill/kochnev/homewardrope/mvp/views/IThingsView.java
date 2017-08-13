package com.kirill.kochnev.homewardrope.mvp.views;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.mvp.views.base.IPaginationView;

import java.util.HashSet;

/**
 * Created by Kirill Kochnev on 25.02.17.
 */

public interface IThingsView extends IPaginationView<Thing> {

    void addThingIdsToAdapter(HashSet<Long> set);

    void setEditMode(boolean isEditMode);

    @StateStrategyType(SkipStrategy.class)
    void navigateToAddUpdateThingView(boolean isEdit, Long id);
}
