package com.kirill.kochnev.homewardrobe.presentation.ui.adapters;

import com.kirill.kochnev.homewardrobe.db.models.IDbModel;

/**
 * Created by kirill on 29.03.17.
 */

public interface OnClick<M extends IDbModel> {
    void onLongClick(M model);

    void onClick(M model);
}