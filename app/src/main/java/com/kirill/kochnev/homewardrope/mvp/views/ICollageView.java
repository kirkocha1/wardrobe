package com.kirill.kochnev.homewardrope.mvp.views;

import android.graphics.Bitmap;
import android.util.SparseArray;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.kirill.kochnev.homewardrope.enums.CollageMode;

/**
 * Created by kirill on 27.04.17.
 */

@StateStrategyType(SkipStrategy.class)
public interface ICollageView extends MvpView {

    void constructView(SparseArray<Bitmap> cache, CollageMode mode);
}
