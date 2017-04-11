package com.kirill.kochnev.homewardrope.ui.adapters.holders;

import android.view.View;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseHolder;
import com.kirill.kochnev.homewardrope.ui.views.ListItemView;

import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 05.04.17.
 */

public class ThingHolder extends BaseHolder<Thing> {

    private HashSet<Long> usedIds;
    private boolean isWardropeMode;

    @BindView(R.id.item)
    public ListItemView item;

    public ThingHolder(View itemView, HashSet<Long> usedIds, boolean isWardropeMode) {
        super(itemView);
        this.usedIds = usedIds;
        this.isWardropeMode = isWardropeMode;
        ButterKnife.bind(this, itemView);
        item.setBoxVisibility(isWardropeMode);
    }

    public void setUsedIds(HashSet<Long> usedIds) {
        this.usedIds = usedIds;
    }

    @Override
    public void beforeClick(View view) {
        if (isWardropeMode) {
            updateusedIds(getModel().getId());
            item.toogleCheck();
        }
    }

    private void updateusedIds(long id) {
        if (usedIds.contains(id)) {
            usedIds.remove(id);
        } else {
            usedIds.add(id);
        }
    }

    @Override
    public void setModel(Thing model) {
        updateBoxes(model);
        super.setModel(model);
    }

    private void updateBoxes(Thing model) {
        if (usedIds != null && usedIds.size() != 0) {
            item.setCheck(usedIds.contains(model.getId()));
        }
    }

}
