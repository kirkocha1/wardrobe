package com.kirill.kochnev.homewardrope.presentation.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.presentation.ui.adapters.base.BaseDbAdapter;
import com.kirill.kochnev.homewardrope.presentation.ui.adapters.holders.LookHolder;

import java.util.HashSet;

/**
 * Created by kirill on 21.04.17.
 */

public class LooksAdapter extends BaseDbAdapter<Look, LookHolder> {

    private HashSet<Long> ids;
    private boolean isEdit;

    public void setIds(HashSet<Long> ids) {
        this.ids = ids;
        notifyDataSetChanged();
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(LookHolder holder, int position) {
        holder.setEdit(isEdit);
        holder.setUsedIds(ids);
        super.onBindViewHolder(holder, position);
    }

    @Override
    public void clear() {
        super.clear();
        ids = null;
        isEdit = false;
    }

    @Override
    public LookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LookHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false), ids);
    }
}
