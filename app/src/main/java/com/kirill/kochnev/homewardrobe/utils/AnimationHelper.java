package com.kirill.kochnev.homewardrobe.utils;


import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.kirill.kochnev.homewardrobe.R;

/**
 * Created by kirill on 28.04.17.
 */

///TODO refactor this class
public class AnimationHelper {
    public static void animateFragmentReplace(FragmentTransaction transaction, Fragment fragment, @IdRes int containerId, String tag) {
        transaction.setCustomAnimations(R.anim.fade_out, R.anim.fade_in);
        if (tag != null) {
            transaction.replace(containerId, fragment, tag).commit();
        } else {
            transaction.replace(containerId, fragment).commit();
        }

    }

    public static void animateFragmentReplace(FragmentTransaction transaction, Fragment fragment, @IdRes int containerId) {
        animateFragmentReplace(transaction, fragment, containerId, null);
    }

    public static void hideShowAnimation(Context context, View view, boolean isHide) {
        view.startAnimation(AnimationUtils.loadAnimation(context,
                isHide ? R.anim.add_btn_hide : R.anim.add_btn_show));
        view.setVisibility(isHide ? View.INVISIBLE : View.VISIBLE);
    }
}
