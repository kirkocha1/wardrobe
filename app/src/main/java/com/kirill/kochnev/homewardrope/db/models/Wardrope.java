package com.kirill.kochnev.homewardrope.db.models;

import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseHolder;
import com.kirill.kochnev.homewardrope.ui.adapters.holders.WardropeHolder;
import com.kirill.kochnev.homewardrope.ui.views.WardropeItemView;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import java.util.Date;
import java.util.HashSet;

import static com.kirill.kochnev.homewardrope.db.WardropeSqlHelper.CREATION_DATE;
import static com.kirill.kochnev.homewardrope.db.tables.WardropeTable.WARDROPE_LOOKS_COUNT;
import static com.kirill.kochnev.homewardrope.db.tables.WardropeTable.WARDROPE_NAME;
import static com.kirill.kochnev.homewardrope.db.tables.WardropeTable.WARDROPE_THINGS_COUNT;

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

    @StorIOSQLiteColumn(name = WARDROPE_THINGS_COUNT)
    int thingsCount;

    @StorIOSQLiteColumn(name = WARDROPE_LOOKS_COUNT)
    int looksCount;

    private HashSet<Long> thingIds;

    private HashSet<Long> lookIds;


    public Wardrope() {
        creationDate = new Date().toString();
    }

    @Override
    public void inflateHolder(BaseHolder holder) {
        WardropeItemView item = ((WardropeHolder) holder).item;
        item.setName(name);
        item.setLooksCount(looksCount);
        item.setThingsCount(thingsCount);
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


    //Use this method in repository for updating looks with this id set
    public String getLookIdsString() {
        StringBuilder result = new StringBuilder();
        if (lookIds != null) {
            for (Long id : lookIds) {
                result.append(result.length() == 0 ? id + "" : "," + id);
            }
        }
        return result.toString();
    }

    public HashSet<Long> getLookIds() {
        return lookIds;
    }

    public void setLookIds(HashSet<Long> lookIds) {
        this.lookIds = lookIds;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getThingsCount() {
        return thingsCount;
    }

    public void setThingsCount(int thingsCount) {
        this.thingsCount = thingsCount;
    }

    public int getLooksCount() {
        return looksCount;
    }

    public void setLooksCount(int looksCount) {
        this.looksCount = looksCount;
    }

}
