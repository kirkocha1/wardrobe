package com.kirill.kochnev.homewardrope.mvp.presenters.base;

import com.kirill.kochnev.homewardrope.db.models.IDbModel;

public interface IPaginator {

    void loadMoreData(final long lastId);

    void onLongItemClick(final IDbModel model);

    void onItemClick(final IDbModel model);

    void putListItem(final long id);
}
