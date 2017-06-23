package com.kirill.kochnev.homewardrope.db.models;

import android.provider.BaseColumns;

import com.kirill.kochnev.homewardrope.db.tables.manytomany.ThingsWardropesTable;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseHolder;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * Created by kirill on 30.03.17.
 */

@StorIOSQLiteType(table = ThingsWardropesTable.THINGS_WARDROPES_TABLE)
public class ThingsWardropes {

    @StorIOSQLiteColumn(key = true, name = BaseColumns._ID)
    Long _id;

    @StorIOSQLiteColumn(name = ThingsWardropesTable.THINGS_WARDROPES_WARDROPES_ID)
    long wardropeId;

    @StorIOSQLiteColumn(name = ThingsWardropesTable.THINGS_WARDROPES_THING_ID)
    long thingId;

    public ThingsWardropes() {
    }

    public ThingsWardropes(long wardropeId, long thingId) {
        this.wardropeId = wardropeId;
        this.thingId = thingId;
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

    public Long getId() {
        return _id;
    }

    public void setId(Long _id) {
        this._id = _id;
    }


}
