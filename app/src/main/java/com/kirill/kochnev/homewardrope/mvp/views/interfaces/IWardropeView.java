package com.kirill.kochnev.homewardrope.mvp.views.interfaces;

import android.content.Intent;
import android.os.Bundle;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;

/**
 * Created by kirill on 30.03.17.
 */

public interface IWardropeView extends IPaginationView<Wardrope> {

    @StateStrategyType(SkipStrategy.class)
    void openUpdateActivity(Intent intent);
}

