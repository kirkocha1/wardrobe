package com.kirill.kochnev.homewardrope.presentation.ui.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.presentation.ui.adapters.OnClick;
import com.kirill.kochnev.homewardrope.presentation.ui.views.ListItemView;

import java.util.HashSet;

/**
 * Created by kirill on 21.04.17.
 */

public class LookHolder extends RecyclerView.ViewHolder implements IItemHolder<Look> {

    private IItemHolder<Look> delegate;
    private ListItemView item;

    public LookHolder(View itemView, HashSet<Long> usedIds) {
        super(itemView);
        item = (ListItemView) itemView.findViewById(R.id.item);
        delegate = new ListItemHolderDelegate<>(item, usedIds);
    }

    @Override
    public void setOnItemClick(OnClick<Look> listener) {
        delegate.setOnItemClick(listener);
    }

    @Override
    public void setEdit(boolean edit) {
        delegate.setEdit(edit);
    }

    @Override
    public void setUsedIds(HashSet<Long> usedIds) {
        delegate.setUsedIds(usedIds);
    }

    @Override
    public void setModel(Look model) {
        delegate.setModel(model);
        item.setName(model.getName());
        item.setImage(model.getIconImagePath());
    }
}
