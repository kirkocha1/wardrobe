package com.kirill.kochnev.homewardrope.db.models;

import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseHolder;

/**
 * Created by Kirill Kochnev on 12.02.17.
 */

public abstract class Look implements IDbModel {

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void inflateHolder(BaseHolder holder) {

    }
}
