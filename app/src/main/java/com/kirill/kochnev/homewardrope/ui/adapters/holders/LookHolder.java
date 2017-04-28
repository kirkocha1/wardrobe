package com.kirill.kochnev.homewardrope.ui.adapters.holders;

import android.view.View;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseHolder;
import com.kirill.kochnev.homewardrope.ui.views.ListItemView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 21.04.17.
 */

public class LookHolder extends BaseHolder<Look> {

    @BindView(R.id.item)
    public ListItemView item;

    public LookHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        item.setBoxVisibility(false);
    }
}
