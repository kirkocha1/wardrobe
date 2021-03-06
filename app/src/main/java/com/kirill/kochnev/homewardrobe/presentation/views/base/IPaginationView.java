package com.kirill.kochnev.homewardrobe.presentation.views.base;

import com.arellomobile.mvp.MvpView;
import com.kirill.kochnev.homewardrobe.db.models.IDbModel;

import java.util.List;

/**
 * Created by Kirill Kochnev on 26.02.17.
 */
public interface IPaginationView<M extends IDbModel> extends MvpView {

    void onLoadFinished(List<M> data);

    void deleteListItem(M model);

    void invalidateListItem(M model);
}
