package com.kirill.kochnev.homewardrope.ui.fragments.base;

import android.content.Context;
import android.support.annotation.StringRes;

import com.arellomobile.mvp.MvpFragment;
import com.kirill.kochnev.homewardrope.ui.activities.base.interfaces.IActionBarController;

public class BaseActionBarFragment extends MvpFragment {

    private IActionBarController barController;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IActionBarController) {
            barController = (IActionBarController) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        barController = null;
    }


    public void setTitle(@StringRes int resId) {
        if (barController != null) {
            barController.setTitle(resId);
        }
    }
}
