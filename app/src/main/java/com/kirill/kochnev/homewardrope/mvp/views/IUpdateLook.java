package com.kirill.kochnev.homewardrope.mvp.views;

import com.arellomobile.mvp.MvpView;
import com.kirill.kochnev.homewardrope.db.models.Look;

/**
 * Created by kirill on 03.05.17.
 */
public interface IUpdateLook extends MvpView {

    void setLookData(Look look);

    void onSave();
}
