package com.kirill.kochnev.homewardrope.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseDbAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.holders.ThingHolder;

import java.util.HashSet;

/**
 * Created by kirill on 05.04.17.
 */

public class ThingsAdapter extends BaseDbAdapter<Thing, ThingHolder> {

    private HashSet<Long> ids;

    private boolean wardropeMode;

    public ThingsAdapter(boolean wardropeMode) {
        this.wardropeMode = wardropeMode;
    }

    public void setIds(HashSet<Long> ids) {
        this.ids = ids;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ThingHolder holder, int position) {
        holder.setUsedIds(ids);
        super.onBindViewHolder(holder, position);
    }

    @Override
    public ThingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ThingHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false), ids, wardropeMode);
    }
}
