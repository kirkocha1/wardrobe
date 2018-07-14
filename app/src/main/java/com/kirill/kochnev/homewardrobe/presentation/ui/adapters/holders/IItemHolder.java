package com.kirill.kochnev.homewardrobe.presentation.ui.adapters.holders;

import com.kirill.kochnev.homewardrobe.db.models.IDbModel;
import com.kirill.kochnev.homewardrobe.presentation.ui.adapters.OnClick;

import java.util.HashSet;

public interface IItemHolder<M extends IDbModel> {

    void setOnItemClick(OnClick<M> listener);

    void setEdit(boolean edit);

    void setUsedIds(HashSet<Long> usedIds);

    void setModel(M model);
}