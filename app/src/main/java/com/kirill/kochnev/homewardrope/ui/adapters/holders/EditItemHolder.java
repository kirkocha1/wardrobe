package com.kirill.kochnev.homewardrope.ui.adapters.holders;

import android.view.View;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseHolder;
import com.kirill.kochnev.homewardrope.ui.views.ListItemView;

import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kirill Kochnev on 15.05.17.
 */

public abstract class EditItemHolder<M extends IDbModel> extends BaseHolder<M> {

    private HashSet<Long> usedIds;
    private boolean isEdit;

    @BindView(R.id.item)
    public ListItemView item;

    public EditItemHolder(View itemView, HashSet<Long> usedIds) {
        super(itemView);
        this.usedIds = usedIds;
        ButterKnife.bind(this, itemView);
    }

    public void setUsedIds(HashSet<Long> usedIds) {
        this.usedIds = usedIds;
    }

    @Override
    public void beforeClick(View view) {
        if (isEdit) {
            updateusedIds(getModel().getId());
            item.toogleCheck();
        }
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
        item.setBoxVisibility(isEdit);
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

    @Override
    public void setModel(M model) {
        updateBoxes(model);
        super.setModel(model);
    }

    private void updateBoxes(M model) {
        if (usedIds != null && usedIds.size() != 0) {
            item.setCheck(usedIds.contains(model.getId()));
        }
    }
}
