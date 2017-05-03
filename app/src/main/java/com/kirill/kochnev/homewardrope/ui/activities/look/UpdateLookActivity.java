package com.kirill.kochnev.homewardrope.ui.activities.look;

import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.UpdateLookPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IUpdateLook;
import com.kirill.kochnev.homewardrope.ui.activities.base.BaseActionBarActivity;

/**
 * Created by kirill on 03.05.17.
 */

public class UpdateLookActivity extends BaseActionBarActivity implements IUpdateLook {

    @InjectPresenter
    UpdateLookPresenter presenter;

    @Override
    public void onInitUi(View baseLayout) {

    }

    @Override
    public boolean isMenuActive() {
        return false;
    }

    @Override
    public boolean isSearchActive() {
        return false;
    }
}
