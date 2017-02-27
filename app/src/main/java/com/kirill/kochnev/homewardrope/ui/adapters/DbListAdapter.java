package com.kirill.kochnev.homewardrope.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.IHolderModel;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.ui.views.ListItemView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kirill Kochnev on 25.02.17.
 */

public class DbListAdapter<M extends IHolderModel> extends RecyclerView.Adapter<DbListAdapter<M>.DbListHolder> {

    private List<M> things;

    public class DbListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item)
        public ListItemView item;

        DbListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setModel(M model) {
            model.inflateHolder(this);
        }
    }

    public  DbListAdapter(List<M> things) {
        this.things = things;
    }

    public void setThings(List<M> things) {
        this.things = things;
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

    @Override
    public int getItemCount() {
        return things.size();
    }

    public M getItem(int position) {
        return things.get(position);
    }
}
