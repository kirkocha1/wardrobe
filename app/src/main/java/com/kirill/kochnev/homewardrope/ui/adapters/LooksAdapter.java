package com.kirill.kochnev.homewardrope.ui.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseDbAdapter;
import com.kirill.kochnev.homewardrope.ui.adapters.holders.LookHolder;

/**
 * Created by kirill on 21.04.17.
 */

public class LooksAdapter extends BaseDbAdapter<Look, LookHolder> {

    @Override
    public LookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LookHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }
}
