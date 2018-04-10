package com.kirill.kochnev.homewardrope.presentation.ui.adapters.holders;

import android.view.View;

import com.kirill.kochnev.homewardrope.db.models.Look;

import java.util.HashSet;

/**
 * Created by kirill on 21.04.17.
 */

public class LookHolder extends EditItemHolder<Look> {

    public LookHolder(View itemView, HashSet<Long> usedIds) {
        super(itemView, usedIds);
    }
}
