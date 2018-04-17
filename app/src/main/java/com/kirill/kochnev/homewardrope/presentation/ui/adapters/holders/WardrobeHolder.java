package com.kirill.kochnev.homewardrope.presentation.ui.adapters.holders;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Wardrobe;
import com.kirill.kochnev.homewardrope.presentation.ui.adapters.OnClick;
import com.kirill.kochnev.homewardrope.presentation.ui.views.WardrobeItemView;

/**
 * Created by kirill on 07.04.17.
 */

public class WardrobeHolder extends RecyclerView.ViewHolder {

    private @NonNull final WardrobeItemView item;
    private @Nullable OnClick<Wardrobe> clickListener;

    public WardrobeHolder(View itemView) {
        super(itemView);
        item = (WardrobeItemView) itemView.findViewById(R.id.wardrope_list_item);
    }

    public void setOnItemClick(@Nullable final OnClick<Wardrobe> listener) {
        clickListener = listener;
    }

    public void setModel(Wardrobe model) {
        item.setName(model.getName());
        item.setLooksCount(model.getLooksCount());
        item.setThingsCount(model.getThingsCount());
        item.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onClick(model);
            }
        });
        item.setOnLongClickListener(v -> {
            if (clickListener != null) {
                clickListener.onLongClick(model);
            }
            return true;
        });
    }
}
