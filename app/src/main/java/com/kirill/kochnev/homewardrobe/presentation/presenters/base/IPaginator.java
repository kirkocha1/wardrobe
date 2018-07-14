package com.kirill.kochnev.homewardrobe.presentation.presenters.base;

import com.kirill.kochnev.homewardrobe.db.models.IDbModel;

public interface IPaginator<M extends IDbModel> {

    void loadMoreData(final long lastId);

    void onLongItemClick(final M model);

    void onItemClick(final M model);

    void putListItem(final long id);
}
