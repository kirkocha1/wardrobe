package com.kirill.kochnev.homewardrope.ui.adapters.holders;

import android.view.View;

import com.kirill.kochnev.homewardrope.R;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseHolder;
import com.kirill.kochnev.homewardrope.ui.views.ListItemView;

import java.util.HashSet;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kirill on 05.04.17.
 */

public class ThingHolder extends EditItemHolder<Thing> {


    public ThingHolder(View itemView, HashSet<Long> usedIds) {
        super(itemView, usedIds);
    }
}
