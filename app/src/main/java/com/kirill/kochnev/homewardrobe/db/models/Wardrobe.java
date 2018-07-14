package com.kirill.kochnev.homewardrobe.db.models;

import android.provider.BaseColumns;

import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import java.util.Date;
import java.util.HashSet;

import static com.kirill.kochnev.homewardrobe.db.WardrobeSqlHelper.CREATION_DATE;
import static com.kirill.kochnev.homewardrobe.db.tables.WardrobeTable.WARDROBE_LOOKS_COUNT;
import static com.kirill.kochnev.homewardrobe.db.tables.WardrobeTable.WARDROBE_NAME;
import static com.kirill.kochnev.homewardrobe.db.tables.WardrobeTable.WARDROBE_TABLE;
import static com.kirill.kochnev.homewardrobe.db.tables.WardrobeTable.WARDROBE_THINGS_COUNT;

/**
 * Created by Kirill Kochnev on 12.02.17.
 */
@StorIOSQLiteType(table = WARDROBE_TABLE)
public class Wardrobe implements IDbModel {

    @StorIOSQLiteColumn(key = true, name = BaseColumns._ID)
    Long _id;

    @StorIOSQLiteColumn(name = CREATION_DATE)
    String creationDate;

    @StorIOSQLiteColumn(name = WARDROBE_NAME)
    String name;

    @StorIOSQLiteColumn(name = WARDROBE_THINGS_COUNT)
    int thingsCount;

    @StorIOSQLiteColumn(name = WARDROBE_LOOKS_COUNT)
    int looksCount;

    private HashSet<Long> thingIds;

    private HashSet<Long> lookIds;


    public Wardrobe() {
        creationDate = new Date().toString();
    }

    public Wardrobe(String name, HashSet<Long> thingIds, HashSet<Long> lookIds) {
        this.name = name;
        this.thingIds = thingIds;
        this.lookIds = lookIds;
        thingsCount = thingIds.size();
        looksCount = lookIds.size();
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
