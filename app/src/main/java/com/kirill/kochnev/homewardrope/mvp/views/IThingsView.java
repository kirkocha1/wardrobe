package com.kirill.kochnev.homewardrope.mvp.views;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.mvp.views.base.IPaginationView;

import java.util.HashSet;

/**
 * Created by Kirill Kochnev on 25.02.17.
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface IThingsView extends IPaginationView<Thing> {

    void addThingIdsToAdapter(HashSet<Long> set);

    void setEditMode(boolean isEditMode);

    void navigateToAddUpdateThingView(boolean isEdit, Long id);
}
