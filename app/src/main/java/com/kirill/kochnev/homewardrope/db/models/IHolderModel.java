package com.kirill.kochnev.homewardrope.db.models;

import com.kirill.kochnev.homewardrope.ui.adapters.DbListAdapter;

/**
 * Created by Kirill Kochnev on 26.02.17.
 */

public interface IHolderModel {
    void inflateHolder(DbListAdapter.DbListHolder holder);
}
