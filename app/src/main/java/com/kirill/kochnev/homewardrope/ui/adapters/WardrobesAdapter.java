package com.kirill.kochnev.homewardrope.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Wardrobe;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseDbAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.holders.WardrobeHolder;

/**
 * Created by kirill on 07.04.17.
 */

public class WardrobesAdapter extends BaseDbAdapter<Wardrobe, WardrobeHolder> {

    @Override
    public WardrobeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WardrobeHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.wardobe_list_item, parent, false));
    }
}
