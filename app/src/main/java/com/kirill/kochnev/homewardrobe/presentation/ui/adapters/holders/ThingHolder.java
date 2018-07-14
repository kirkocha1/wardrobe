package com.kirill.kochnev.homewardrobe.presentation.ui.adapters.holders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kirill.kochnev.homewardrobe.R;
import com.kirill.kochnev.homewardrobe.db.models.Thing;
import com.kirill.kochnev.homewardrobe.presentation.ui.adapters.OnClick;
import com.kirill.kochnev.homewardrobe.presentation.ui.views.ListItemView;

import java.util.HashSet;

/**
 * Created by kirill on 05.04.17.
 */

public class ThingHolder extends RecyclerView.ViewHolder {

    private final @NonNull IItemHolder<Thing> delegate;

    private final @NonNull ListItemView item;

    public ThingHolder(View itemView, HashSet<Long> usedIds) {
        super(itemView);
        item = (ListItemView) itemView.findViewById(R.id.item);
        delegate = new ListItemHolderDelegate<>(item, usedIds);
    }

    public void setOnItemClick(OnClick<Thing> listener) {
        delegate.setOnItemClick(listener);
    }

    public void setEdit(boolean edit) {
        delegate.setEdit(edit);
    }

    public void setUsedIds(HashSet<Long> usedIds) {
        delegate.setUsedIds(usedIds);
    }

    public void setModel(Thing model) {
        delegate.setModel(model);
        item.setName(model.getName());
        item.setImage(model.getIconImagePath());
    }
}
