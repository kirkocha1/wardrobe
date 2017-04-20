package com.kirill.kochnev.homewardrope.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseDbAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.holders.WardropeHolder;

/**
 * Created by kirill on 07.04.17.
 */

public class WardropesAdapter extends BaseDbAdapter<Wardrope, WardropeHolder> {

    @Override
    public WardropeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WardropeHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.wardope_list_item, parent, false));
    }
}
