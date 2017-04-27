package com.kirill.kochnev.homewardrope.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by kirill on 27.04.17.
 */


@StateStrategyType(SkipStrategy.class)
public interface IFirstStepCreationLookView extends MvpView {

    void addThingId(long id);

    void openCollageFragment(HashSet<Long> thingIds);
}
