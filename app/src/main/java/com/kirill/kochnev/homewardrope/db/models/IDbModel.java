package com.kirill.kochnev.homewardrope.db.models;

import android.provider.BaseColumns;

import com.kirill.kochnev.homewardrope.ui.adapters.BaseHolder;
import com.kirill.kochnev.homewardrope.ui.adapters.DbListAdapter;

/**
 * Created by kirill on 03.04.17.
 */

public interface IDbModel extends BaseColumns {
    Long getId();

    void inflateHolder(BaseHolder holder);
}
