package com.kirill.kochnev.homewardrope.presentation.ui.adapters.holders;

import android.view.View;

import com.kirill.kochnev.homewardrope.db.models.Thing;

import java.util.HashSet;

/**
 * Created by kirill on 05.04.17.
 */

public class ThingHolder extends EditItemHolder<Thing> {


    public ThingHolder(View itemView, HashSet<Long> usedIds) {
        super(itemView, usedIds);
    }
}
