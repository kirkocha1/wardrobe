package com.kirill.kochnev.homewardrope.db.models;

import android.provider.BaseColumns;

import com.kirill.kochnev.homewardrope.ui.adapters.DbListAdapter;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import java.util.Date;
import java.util.List;

/**
 * Created by Kirill Kochnev on 12.02.17.
 */
@StorIOSQLiteType(table = "wardropes")
public class Wardrope implements IDbModel {

    @StorIOSQLiteColumn(key = true, name = BaseColumns._ID)
    Long _id;

    @StorIOSQLiteColumn(name = "creation_date")
    String creationDate;

    @StorIOSQLiteColumn(name = "name")
    String name;

    private List<Thing> things;

    public Wardrope() {
    }

    @Override
    public void inflateHolder(DbListAdapter.DbListHolder holder) {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }


    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate.toString();
    }

    public List<Thing> getThings() {
        return things;
    }

    public void setThings(List<Thing> things) {
        this.things = things;
    }
}
