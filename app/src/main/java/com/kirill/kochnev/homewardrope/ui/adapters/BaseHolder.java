package com.kirill.kochnev.homewardrope.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.ui.views.ListItemView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 05.04.17.
 */

public class BaseHolder<M extends IDbModel> extends RecyclerView.ViewHolder {

    @BindView(R.id.item)
    public ListItemView item;

    BaseHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
//        item.setBoxVisibility(isWardropeMode);
    }

    public void setOnItmeClick(View.OnClickListener listener) {
        itemView.setOnClickListener(listener);
    }

    public void setOnLongItmeClick(View.OnLongClickListener listener) {
        itemView.setOnLongClickListener(listener);
    }

    public void setModel(M model) {
        model.inflateHolder(this);
    }
}
