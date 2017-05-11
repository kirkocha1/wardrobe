package com.kirill.kochnev.homewardrope.utils;


import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.WardropeApplication;

/**
 * Created by kirill on 28.04.17.
 */

public class AnimationHelper {
    public static void animateFragmentReplace(FragmentTransaction transaction, Fragment fragment, @IdRes int containerId) {
//        transaction.setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right);
        transaction.replace(containerId, fragment).commit();
    }

    public static void hideShowAnimation(View view, boolean isHide) {
        view.startAnimation(AnimationUtils.loadAnimation(WardropeApplication.getContext(),
                isHide ? R.anim.add_btn_hide : R.anim.add_btn_show));
        view.setVisibility(isHide ? View.GONE : View.VISIBLE);
    }
}
