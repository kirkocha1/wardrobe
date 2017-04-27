package com.kirill.kochnev.homewardrope.mvp.views;

import android.content.Intent;

import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.mvp.views.base.IPaginationView;

import java.util.HashSet;

/**
 * Created by Kirill Kochnev on 25.02.17.
 */

@StateStrategyType(SingleStateStrategy.class)
public interface IThingsView extends IPaginationView<Thing> {

    @StateStrategyType(SkipStrategy.class)
    void openUpdateActivity(Intent bundle);

    void addThingId(long id);

    void addThingIdsToAdapter(HashSet<Long> set);

    void setEditMode(boolean isEditMode);
}
