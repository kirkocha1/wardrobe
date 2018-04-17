package com.kirill.kochnev.homewardrope.presentation.ui.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.presentation.ui.adapters.base.IListAdapterDelegate;
import com.kirill.kochnev.homewardrope.presentation.ui.adapters.base.CommonAdapterDelegate;
import com.kirill.kochnev.homewardrope.presentation.ui.adapters.holders.ThingHolder;

import java.util.HashSet;
import java.util.List;

/**
 * Created by kirill on 05.04.17.
 */

public class ThingsAdapter extends RecyclerView.Adapter<ThingHolder> implements IListAdapterDelegate<Thing> {

    private @Nullable HashSet<Long> ids;
    private boolean isEdit;
    private @NonNull final IListAdapterDelegate<Thing> delegate;
    private @Nullable OnClick<Thing> clickListener;


    public ThingsAdapter() {
        delegate = new CommonAdapterDelegate<>();
    }

    public void setIds(@Nullable final HashSet<Long> ids) {
        this.ids = ids;
        notifyDataSetChanged();
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
        notifyDataSetChanged();
    }

    @Override
    public ThingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ThingHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false), ids);
    }

    @Override
    public void onBindViewHolder(ThingHolder holder, int position) {
        holder.setEdit(isEdit);
        if (ids == null) {
            ids = new HashSet<>();
        }
        holder.setUsedIds(ids);
        holder.setModel(getItem(position));
        holder.setOnItemClick(clickListener);
    }

    @Override
    public void addData(List<Thing> models) {
        delegate.addData(models);
        notifyDataSetChanged();
    }

    @Override
    public int addData(Thing model) {
        int position = delegate.addData(model);
        notifyItemChanged(position);
        return position;
    }

    @Override
    public int getItemCount() {
        return delegate.getItemCount();
    }

    @Override
    public Thing getItem(int position) {
        return delegate.getItem(position);
    }

    @Override
    public long getLastId() {
        return delegate.getLastId();
    }

    @Override
    public int onRemoveItem(Thing model) {
        int position = delegate.onRemoveItem(model);
        notifyItemRemoved(position);
        return position;
    }

    public void clear() {
        delegate.clear();
        ids = null;
        isEdit = false;
    }

    public void setClickListener(@Nullable OnClick<Thing> clickListner) {
        clickListener = clickListner;
    }

}
