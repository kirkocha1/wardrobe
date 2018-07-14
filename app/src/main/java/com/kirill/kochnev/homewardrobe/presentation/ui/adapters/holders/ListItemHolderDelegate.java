package com.kirill.kochnev.homewardrobe.presentation.ui.adapters.holders;

import android.support.annotation.NonNull;
import android.view.View;

import com.kirill.kochnev.homewardrobe.db.models.IDbModel;
import com.kirill.kochnev.homewardrobe.presentation.ui.adapters.OnClick;
import com.kirill.kochnev.homewardrobe.presentation.ui.views.ListItemView;

import java.util.HashSet;

public class ListItemHolderDelegate<M extends IDbModel> implements IItemHolder<M> {

    private M model;
    private final ListItemView item;
    private boolean isEdit;
    private HashSet<Long> usedIds;

    ListItemHolderDelegate(@NonNull ListItemView view, HashSet<Long> usedIds) {
        this.usedIds = usedIds;
        this.item = view;
    }

    @Override
    public void setOnItemClick(OnClick<M> listener) {
        item.setOnClickListener(v -> {
            if (listener != null) {
                beforeClick(v);
                listener.onClick(model);
            }
        });
        item.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onLongClick(model);
            }
            return true;
        });
    }

    private void beforeClick(View view) {
        if (isEdit) {
            updateusedIds(model.getId());
            item.toogleCheck();
        }
    }

    @Override
    public void setEdit(boolean edit) {
        isEdit = edit;
        item.setBoxVisibility(isEdit);
    }

    @Override
    public void setUsedIds(HashSet<Long> usedIds) {
        this.usedIds = usedIds;
    }

    @Override
    public void setModel(M model) {
        updateBoxes(model);
        this.model = model;
    }

    private void updateusedIds(long id) {
        if (usedIds != null) {
            if (usedIds.contains(id)) {
                usedIds.remove(id);
            } else {
                usedIds.add(id);
            }
        }
    }

    private void updateBoxes(M model) {
        if (usedIds != null && usedIds.size() != 0) {
            item.setCheck(usedIds.contains(model.getId()));
        }
    }
}
