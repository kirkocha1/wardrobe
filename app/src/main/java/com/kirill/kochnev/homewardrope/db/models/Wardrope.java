package com.kirill.kochnev.homewardrope.db.models;

import android.provider.BaseColumns;

import com.kirill.kochnev.homewardrope.ui.adapters.DbListAdapter;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static com.kirill.kochnev.homewardrope.db.WardropeSqlHelper.CREATION_DATE;
import static com.kirill.kochnev.homewardrope.db.tables.WardropeTable.WARDROPE_COUNT;
import static com.kirill.kochnev.homewardrope.db.tables.WardropeTable.WARDROPE_NAME;

/**
 * Created by Kirill Kochnev on 12.02.17.
 */
@StorIOSQLiteType(table = "wardropes")
public class Wardrope implements IDbModel {

    @StorIOSQLiteColumn(key = true, name = BaseColumns._ID)
    Long _id;

    @StorIOSQLiteColumn(name = CREATION_DATE)
    String creationDate;

    @StorIOSQLiteColumn(name = WARDROPE_NAME)
    String name;

    @StorIOSQLiteColumn(name = WARDROPE_COUNT)
    int count;

    private HashSet<Long> thingIds;

    public Wardrope() {
    }

    @Override
    public void inflateHolder(DbListAdapter.DbListHolder holder) {
        holder.item.setName(name);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long _id) {
        this._id = _id;
    }


    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate.toString();
    }

    public HashSet<Long> getThingIds() {
        return thingIds;
    }

    public void setThingIds(HashSet<Long> thingIds) {
        this.thingIds = thingIds;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
