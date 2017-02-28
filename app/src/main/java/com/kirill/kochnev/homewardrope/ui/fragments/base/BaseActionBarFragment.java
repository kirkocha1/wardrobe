package com.kirill.kochnev.homewardrope.ui.fragments.base;

import android.content.Context;

import com.arellomobile.mvp.MvpFragment;
import com.kirill.kochnev.homewardrope.ui.activities.base.interfaces.IActionBarController;

public class BaseActionBarFragment extends MvpFragment {

    private IActionBarController barController;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        barController = (IActionBarController) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        barController = null;
    }


    public void setTitle() {
        if (barController != null) {
//            barController.setTitle();
        }
    }
}
