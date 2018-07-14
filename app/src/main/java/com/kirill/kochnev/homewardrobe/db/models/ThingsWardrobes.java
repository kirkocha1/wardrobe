package com.kirill.kochnev.homewardrobe.db.models;

import android.provider.BaseColumns;

import com.kirill.kochnev.homewardrobe.db.tables.manytomany.ThingsWardropesTable;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * Created by kirill on 30.03.17.
 */

@StorIOSQLiteType(table = ThingsWardropesTable.THINGS_WARDROBES_TABLE)
public class ThingsWardrobes {

    @StorIOSQLiteColumn(key = true, name = BaseColumns._ID)
    Long _id;

    @StorIOSQLiteColumn(name = ThingsWardropesTable.THINGS_WARDROBES_WARDROBES_ID)
    long wardrobeId;

    @StorIOSQLiteColumn(name = ThingsWardropesTable.THINGS_WARDROBES_THING_ID)
    long thingId;

    public ThingsWardrobes() {
    }

    public ThingsWardrobes(long wardrobeId, long thingId) {
        this.wardrobeId = wardrobeId;
        this.thingId = thingId;
    }

    public Long getWardrobeId() {
        return this.wardrobeId;
    }

    public void setWardrobeId(Long wardrobeId) {
        this.wardrobeId = wardrobeId;
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
