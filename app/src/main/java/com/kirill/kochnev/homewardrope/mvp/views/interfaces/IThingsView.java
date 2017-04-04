package com.kirill.kochnev.homewardrope.mvp.views.interfaces;

import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kirill.kochnev.homewardrope.db.models.Thing;

import java.util.List;

/**
 * Created by Kirill Kochnev on 25.02.17.
 */

public interface IThingsView extends IPaginationView<Thing> {

    @StateStrategyType(SkipStrategy.class)
    void initList(List<Thing> models, boolean isWardropeMode, long wardropeId);

    @StateStrategyType(SkipStrategy.class)
    void openUpdateActivity(Intent bundle);

    void addThingId(long id);

}
