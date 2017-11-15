package com.kirill.kochnev.homewardrope.mvp.views.base;

import com.arellomobile.mvp.MvpView;

import java.util.List;

/**
 * Created by Kirill Kochnev on 26.02.17.
 */
public interface IPaginationView<M> extends MvpView {

    void onLoadFinished(List<M> data);

    void deleteListItem(M model);

    void invalidateListItem(M model);
}
