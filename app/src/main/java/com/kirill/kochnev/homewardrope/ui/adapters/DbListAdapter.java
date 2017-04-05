package com.kirill.kochnev.homewardrope.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.ui.views.ListItemView;

import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kirill Kochnev on 25.02.17.
 */

public class DbListAdapter<M extends IDbModel> extends RecyclerView.Adapter<DbListAdapter<M>.DbListHolder> {

    private List<M> models;

    private OnClick<M> clickListner;

    private boolean isWardropeMode;

    private HashSet<Long> usedIds;

    public void setUsedIds(HashSet<Long> usedIds) {
        this.usedIds = usedIds;
        notifyDataSetChanged();
    }

    public class DbListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item)
        public ListItemView item;

        DbListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {
                if (clickListner != null) {
                    M model = DbListAdapter.this.getItem(getLayoutPosition());
                    if (isWardropeMode) {
                        updateusedIds(model.getId());
                        item.toogleCheck();
                    }
                    clickListner.onClick(model);
                }
            });

            itemView.setOnLongClickListener(v -> {
                if (clickListner != null) {
                    clickListner.onLongClick(DbListAdapter.this.getItem(getLayoutPosition()));
                }
                return true;
            });

            item.setBoxVisibility(isWardropeMode);
        }

        //TODO temporary solution need to refactor, has two identical sets in different places
        private void updateusedIds(long id) {
            if (usedIds.contains(id)) {
                usedIds.remove(id);
            } else {
                usedIds.add(id);
            }
        }

        public void setModel(M model) {
            updateBoxes(model);
            model.inflateHolder(this);
        }

        private void updateBoxes(M model) {
            if (usedIds != null && usedIds.size() != 0) {
                item.setCheck(usedIds.contains(model.getId()));
            }
        }
    }

    public void setWardropeMode(boolean wardropeMode) {
        isWardropeMode = wardropeMode;
    }

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
    public DbListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DbListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(DbListHolder holder, int position) {
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
