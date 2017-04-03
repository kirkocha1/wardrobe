package com.kirill.kochnev.homewardrope.db.models;

import android.provider.BaseColumns;

import com.kirill.kochnev.homewardrope.ui.adapters.DbListAdapter;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * Created by kirill on 30.03.17.
 */

@StorIOSQLiteType(table = "things_wardropes")
public class ThingsWardropes implements IDbModel {

    @StorIOSQLiteColumn(key = true, name = BaseColumns._ID)
    Long _id;

    @StorIOSQLiteColumn(name = "wardropeId")
    long wardropeId;

    @StorIOSQLiteColumn(name = "thingId")
    long thingId;

    public ThingsWardropes() {
    }

    public Long getWardropeId() {
        return this.wardropeId;
    }

    public void setWardropeId(Long wardropeId) {
        this.wardropeId = wardropeId;
    }

    public Long getThingId() {
        return this.thingId;
    }

    public void setThingId(Long thingId) {
        this.thingId = thingId;
    }

    @Override
    public void inflateHolder(DbListAdapter.DbListHolder holder) {

    }
}
