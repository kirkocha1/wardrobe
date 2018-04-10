package com.kirill.kochnev.homewardrope.presentation.ui.adapters.holders;

import android.view.View;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Wardrobe;
import com.kirill.kochnev.homewardrope.presentation.ui.adapters.base.BaseHolder;
import com.kirill.kochnev.homewardrope.presentation.ui.views.WardrobeItemView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 07.04.17.
 */

public class WardrobeHolder extends BaseHolder<Wardrobe> {

    @BindView(R.id.wardrope_list_item)
    public WardrobeItemView item;

    public WardrobeHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
