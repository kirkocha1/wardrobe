package com.kirill.kochnev.homewardrobe.presentation.ui.adapters.base;

import android.support.annotation.Nullable;

import com.kirill.kochnev.homewardrobe.db.models.IDbModel;
import com.kirill.kochnev.homewardrobe.presentation.ui.adapters.OnClick;

import java.util.ArrayList;
import java.util.List;

public class CommonAdapterDelegate<M extends IDbModel> implements IListAdapterDelegate<M> {

    private @Nullable List<M> models;

    public void addData(List<M> model) {
        getModels().addAll(model);
    }

    @Override
    public int addData(M model) {
        int position = 0;
        if (this.models == null) {
            this.models = new ArrayList<>();
            models.add(model);
        } else {
            position = insertItem(model);
        }
        return position;
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

    private List<M> getModels() {
        if (models == null) {
            models = new ArrayList<>();
        }
        return models;
    }

    @Override
    public int getItemCount() {
        return models == null ? 0 : models.size();
    }

    @Override
    public M getItem(int position) {
        return models.get(position);
    }

    @Override
    public long getLastId() {
        return getItem(getItemCount() - 1).getId();
    }

    @Override
    public int onRemoveItem(M model) {
        int position = models.indexOf(model);
        models.remove(model);
        return position;
    }

    @Override
    public void clear() {
        models = null;
    }

    @Override
    public void setClickListener(OnClick<M> clickListner) {

    }
}