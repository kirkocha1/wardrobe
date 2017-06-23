package com.kirill.kochnev.homewardrope.ui.adapters;

/**
 * Created by kirill on 29.03.17.
 */

public interface OnClick<M> {
    void onLongClick(M model);

    void onClick(M model);
}