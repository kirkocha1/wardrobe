package com.kirill.kochnev.homewardrope.ui.adapters.base;

import android.support.v7.widget.RecyclerView;

import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.ui.adapters.OnClick;

import java.util.ArrayList;
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

    public void addData(List<M> models) {
        if (this.models == null) {
            this.models = models;
        } else {
            this.models.addAll(models);
        }
        notifyDataSetChanged();
    }

    public void addData(M model) {
        int position = 0;
        if (this.models == null) {
            this.models = new ArrayList<>();
            models.add(model);
        } else {
            position = insertItem(model);
        }
        notifyItemChanged(position);
    }

    private int insertItem(M item) {
        int position = 0;
        boolean isInserted = false;
        for (M model : models) {
            if (item.getId().equals(model.getId())) {
                models.add(position, item);
                models.remove(model);
                isInserted = true;
                break;
            }
            position++;
        }
        if (!isInserted) {
            models.add(item);
            position = models.indexOf(item);
        }
        return position;
    }

    @Override
    public void onBindViewHolder(H holder, int position) {
        holder.setModel(getItem(position));
        holder.setOnItemClick(clickListner);
    }

    public void onRemoveItem(M model) {
        int position = models.indexOf(model);
        models.remove(model);
        notifyItemRemoved(position);
    }

    public void clear() {
        models = null;
    }

    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }

    public M getItem(int position) {
        return models.get(position);
    }


    public long getLastId() {
        return getItem(getItemCount() - 1).getId();
    }
}
