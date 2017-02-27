package com.kirill.kochnev.homewardrope.mvp.views.interfaces;

import com.arellomobile.mvp.MvpView;
import com.kirill.kochnev.homewardrope.db.models.Thing;

import java.util.List;

/**
 * Created by Kirill Kochnev on 25.02.17.
 */

public interface IThingsView extends MvpView {

    void initList(List<Thing> things);
}
