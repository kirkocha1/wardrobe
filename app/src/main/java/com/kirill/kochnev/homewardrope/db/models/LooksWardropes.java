package com.kirill.kochnev.homewardrope.db.models;

import android.provider.BaseColumns;

import com.kirill.kochnev.homewardrope.db.tables.manytomany.LooksWardropesTable;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

/**
 * Created by kirill on 21.04.17.
 */


@StorIOSQLiteType(table = LooksWardropesTable.LOOKS_WARDROPES_TABLE)
public class LooksWardropes {

    @StorIOSQLiteColumn(key = true, name = BaseColumns._ID)
    Long _id;

    @StorIOSQLiteColumn(name = LooksWardropesTable.LOOKS_WARDROPES_WARDROPES_ID)
    long wardropeId;

    @StorIOSQLiteColumn(name = LooksWardropesTable.LOOKS_WARDROPES_LOOK_ID)
    long lookId;

    public LooksWardropes() {
    }

    public LooksWardropes(long wardropeId, long thingId) {
        this.wardropeId = wardropeId;
        this.lookId = thingId;
    }

    public Long getWardropeId() {
        return this.wardropeId;
    }

    public void setWardropeId(Long wardropeId) {
        this.wardropeId = wardropeId;
    }

    public Long getLookId() {
        return this.lookId;
    }

    public void setLookId(Long lookId) {
        this.lookId = lookId;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long _id) {
        this._id = _id;
    }

}
