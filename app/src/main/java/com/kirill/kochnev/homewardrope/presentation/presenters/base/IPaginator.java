package com.kirill.kochnev.homewardrope.presentation.presenters.base;

import com.kirill.kochnev.homewardrope.db.models.IDbModel;

public interface IPaginator<M extends IDbModel> {

    void loadMoreData(final long lastId);

    void onLongItemClick(final M model);

    void onItemClick(final M model);

    void putListItem(final long id);
}
