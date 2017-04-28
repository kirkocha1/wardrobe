package com.kirill.kochnev.homewardrope.utils;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.WardropeApplication;

/**
 * Created by kirill on 28.04.17.
 */

public class AnimationHelper {
    public static void animateFragmentReplace(FragmentManager manager, Fragment fragment, @IdRes int containerId) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_out_left, R.animator.slide_in_right);
        transaction.replace(R.id.content, fragment).commit();
        manager.executePendingTransactions();
    }

    public static void hideShowAnimation(View view, boolean isHide) {
        view.startAnimation(AnimationUtils.loadAnimation(WardropeApplication.getContext(),
                isHide ? R.anim.add_btn_hide : R.anim.add_btn_show));
        view.setVisibility(isHide ? View.GONE : View.VISIBLE);
    }
}
