package com.kirill.kochnev.homewardrope.presentation.ui.adapters.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.presentation.ui.adapters.OnClick;

/**
 * Created by kirill on 05.04.17.
 */

public abstract class BaseHolder<M extends IDbModel> extends RecyclerView.ViewHolder {

    private M model;

    public BaseHolder(View itemView) {
        super(itemView);
    }

    public void setOnItemClick(OnClick<M> listener) {
        itemView.setOnClickListener(v -> {
            if (listener != null) {
                beforeClick(v);
                listener.onClick(model);
            }
        });
        itemView.setOnLongClickListener(v -> {
            if (listener != null) {
                beforeLongClick(v);
                listener.onLongClick(model);
            }
            return true;
        });
    }

    public void beforeClick(View view) {

    }

    public void beforeLongClick(View view) {

    }

    public M getModel() {
        return model;
    }

    public void setModel(M model) {
        this.model = model;
        model.inflateHolder(this);
    }
}
