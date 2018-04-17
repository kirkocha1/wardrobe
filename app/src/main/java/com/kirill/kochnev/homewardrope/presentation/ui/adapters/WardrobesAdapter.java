package com.kirill.kochnev.homewardrope.presentation.ui.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Wardrobe;
import com.kirill.kochnev.homewardrope.presentation.ui.adapters.base.IListAdapterDelegate;
import com.kirill.kochnev.homewardrope.presentation.ui.adapters.base.CommonAdapterDelegate;
import com.kirill.kochnev.homewardrope.presentation.ui.adapters.holders.WardrobeHolder;

import java.util.List;

/**
 * Created by kirill on 07.04.17.
 */

public class WardrobesAdapter extends RecyclerView.Adapter<WardrobeHolder> implements IListAdapterDelegate<Wardrobe> {

    private @NonNull IListAdapterDelegate<Wardrobe> delegate;
    private @Nullable OnClick<Wardrobe> onClick;

    public WardrobesAdapter() {
        delegate = new CommonAdapterDelegate<>();
    }

    @Override
    public WardrobeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WardrobeHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.wardobe_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(WardrobeHolder holder, int position) {
        holder.setModel(getItem(position));
        holder.setOnItemClick(onClick);
    }

    @Override
    public void addData(List<Wardrobe> models) {
        delegate.addData(models);
        notifyDataSetChanged();
    }

    @Override
    public int addData(Wardrobe model) {
        int position = delegate.addData(model);
        notifyItemChanged(position);
        return position;
    }

    @Override
    public int getItemCount() {
        return delegate.getItemCount();
    }

    @Override
    public Wardrobe getItem(int position) {
        return delegate.getItem(position);
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public long getLastId() {
        return delegate.getLastId();
    }

    @Override
    public int onRemoveItem(Wardrobe model) {
        int position = delegate.onRemoveItem(model);
        notifyItemRemoved(position);
        return position;
    }

    @Override
    public void setClickListener(OnClick<Wardrobe> clickListner) {
        onClick = clickListner;
    }
}
