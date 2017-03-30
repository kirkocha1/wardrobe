package com.kirill.kochnev.homewardrope.mvp.views.interfaces;

import com.arellomobile.mvp.MvpView;

/**
 * Created by kirill on 30.03.17.
 */

public interface IAddUpdateWardropeView extends MvpView {
    void setCount(int count);
    void addThingId(long id);
}
