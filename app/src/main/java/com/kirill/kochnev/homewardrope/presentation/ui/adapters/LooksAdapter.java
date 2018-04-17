package com.kirill.kochnev.homewardrope.presentation.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.presentation.ui.adapters.base.IListAdapterDelegate;
import com.kirill.kochnev.homewardrope.presentation.ui.adapters.base.CommonAdapterDelegate;
import com.kirill.kochnev.homewardrope.presentation.ui.adapters.holders.LookHolder;

import java.util.HashSet;
import java.util.List;

/**
 * Created by kirill on 21.04.17.
 */

public class LooksAdapter extends RecyclerView.Adapter<LookHolder> implements IListAdapterDelegate<Look> {

    private HashSet<Long> ids;
    private boolean isEdit;
    private IListAdapterDelegate<Look> delegate;
    private OnClick<Look> onClick;

    public LooksAdapter() {
        delegate = new CommonAdapterDelegate<>();
    }

    public void setIds(HashSet<Long> ids) {
        this.ids = ids;
        notifyDataSetChanged();
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
        notifyDataSetChanged();
    }

    @Override
    public LookHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new LookHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false), ids);
    }

    @Override
    public void onBindViewHolder(LookHolder holder, int position) {
        holder.setEdit(isEdit);
        if (ids == null) {
            ids = new HashSet<>();
        }
        holder.setUsedIds(ids);
        holder.setModel(getItem(position));
        holder.setOnItemClick(onClick);
    }

    public void clear() {
        delegate.clear();
        ids = null;
        isEdit = false;
    }

    @Override
    public void addData(List<Look> models) {
        delegate.addData(models);
        notifyDataSetChanged();
    }

    @Override
    public int addData(Look model) {
        int position = delegate.addData(model);
        notifyItemChanged(position);
        return position;
    }

    @Override
    public int getItemCount() {
        return delegate.getItemCount();
    }

    @Override
    public Look getItem(int position) {
        return delegate.getItem(position);
    }

    @Override
    public long getLastId() {
        return delegate.getLastId();
    }

    @Override
    public int onRemoveItem(Look model) {
        int position = delegate.onRemoveItem(model);
        notifyItemRemoved(position);
        return position;
    }

    @Override
    public void setClickListener(OnClick<Look> clickListner) {
        onClick = clickListner;
    }
}
