package com.kirill.kochnev.homewardrope.ui.adapters;

import android.support.v7.widget.RecyclerView;

import com.kirill.kochnev.homewardrope.db.models.IDbModel;

import java.util.List;

/**
 * Created by kirill on 05.04.17.
 */

public abstract class BaseDbAdapter<M extends IDbModel, H extends BaseHolder<M>> extends RecyclerView.Adapter<H> {

    private List<M> models;

    private OnClick<M> clickListner;

    public void setClickListner(OnClick<M> clickListner) {
        this.clickListner = clickListner;
    }

    public void setData(List<M> models) {
        this.models = models;
        notifyDataSetChanged();
    }

    public void addData(List<M> models) {
        this.models.addAll(models);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(H holder, int position) {
        holder.setModel(getItem(position));
    }

    public void onRemoveItem(M model) {
        models.remove(model);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }

    public M getItem(int position) {
        return models.get(position);
    }
}
