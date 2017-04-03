package com.kirill.kochnev.homewardrope.db.models;

import com.kirill.kochnev.homewardrope.ui.adapters.DbListAdapter;

/**
 * Created by Kirill Kochnev on 12.02.17.
 */

public class Look implements IDbModel {

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void inflateHolder(DbListAdapter.DbListHolder holder) {

    }
}
