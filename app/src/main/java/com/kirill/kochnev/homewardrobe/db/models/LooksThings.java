package com.kirill.kochnev.homewardrobe.db.models;

import android.provider.BaseColumns;

import com.kirill.kochnev.homewardrobe.db.tables.manytomany.LooksThingsTable;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * Created by kirill on 21.04.17.
 */


@StorIOSQLiteType(table = LooksThingsTable.LOOKS_THINGS_TABLE)
public class LooksThings {

    @StorIOSQLiteColumn(key = true, name = BaseColumns._ID)
    Long _id;

    @StorIOSQLiteColumn(name = LooksThingsTable.LOOKS_THINGS_LOOK_ID)
    long lookId;

    @StorIOSQLiteColumn(name = LooksThingsTable.LOOKS_THINGS_THING_ID)
    long thingId;

    public LooksThings() {
    }

    public LooksThings(long lookId, long thingId) {
        this.lookId = lookId;
        this.thingId = thingId;
    }

    public Long getLookId() {
        return this.lookId;
    }

    public void setLookId(Long lookId) {
        this.lookId = lookId;
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
