package com.kirill.kochnev.homewardrobe.presentation.ui.adapters.base;

import com.kirill.kochnev.homewardrobe.db.models.IDbModel;
import com.kirill.kochnev.homewardrobe.presentation.ui.adapters.OnClick;

import java.util.List;

public interface IListAdapterDelegate<M extends IDbModel> {
    void addData(List<M> models);

    int addData(M model);

    int getItemCount();

    M getItem(int position);

    long getLastId();

    int onRemoveItem(M model);

    void clear();

    void setClickListener(OnClick<M> clickListner);

}