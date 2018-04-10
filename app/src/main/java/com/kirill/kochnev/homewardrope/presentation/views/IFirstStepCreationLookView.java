package com.kirill.kochnev.homewardrope.presentation.views;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.HashSet;

/**
 * Created by kirill on 27.04.17.
 */

@StateStrategyType(SkipStrategy.class)
public interface IFirstStepCreationLookView extends MvpView {

    void showSaveDialog(String name, String tag);

    void openCollageFragment(HashSet<Long> thingIds);

    void onSuccess(long lookId);

    void showError(boolean isMin);

    void setBtnsState(boolean isCreateVisible, boolean isSaveVisible, boolean isAllThingsVisible);

}
