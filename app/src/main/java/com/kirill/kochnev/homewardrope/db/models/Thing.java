package com.kirill.kochnev.homewardrope.db.models;

import android.provider.BaseColumns;

import com.kirill.kochnev.homewardrope.db.WardrobeSqlHelper;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import java.util.Date;

import static com.kirill.kochnev.homewardrope.db.tables.ThingsTable.THINGS_TABLE;
import static com.kirill.kochnev.homewardrope.db.tables.ThingsTable.THING_FULL_IMAGE_PATH;
import static com.kirill.kochnev.homewardrope.db.tables.ThingsTable.THING_ICON_IMAGE_PATH;
import static com.kirill.kochnev.homewardrope.db.tables.ThingsTable.THING_NAME;
import static com.kirill.kochnev.homewardrope.db.tables.ThingsTable.THING_TAG;

/**
 * Created by Kirill Kochnev on 12.02.17.
 */


@StorIOSQLiteType(table = THINGS_TABLE)
public class Thing extends BitmapModel implements IDbModel {

    @StorIOSQLiteColumn(key = true, name = BaseColumns._ID)
    Long _id;

    @StorIOSQLiteColumn(name = WardrobeSqlHelper.CREATION_DATE)
    String creationDate;

    @StorIOSQLiteColumn(name = THING_NAME)
    String name;

    @StorIOSQLiteColumn(name = THING_TAG)
    String tag;

    @StorIOSQLiteColumn(name = THING_FULL_IMAGE_PATH)
    String fullImagePath;

    @StorIOSQLiteColumn(name = THING_ICON_IMAGE_PATH)
    String iconImagePath;

    public Thing() {
        this.creationDate = new Date().toString();
    }

    public Thing(long id) {
        this._id = id;
        this.creationDate = new Date().toString();
    }


    public Long getId() {
        return _id;
    }

    public void setId(Long _id) {
        this._id = _id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFullImagePath() {
        return this.fullImagePath;
    }

    public void setFullImagePath(String fullImagePath) {
        this.fullImagePath = fullImagePath;
    }

    public String getIconImagePath() {
        return this.iconImagePath;
    }

    public void setIconImagePath(String iconImagePath) {
        this.iconImagePath = iconImagePath;
    }

}
