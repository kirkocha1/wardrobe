package com.kirill.kochnev.homewardrope.db.models;

import android.provider.BaseColumns;

import com.kirill.kochnev.homewardrope.db.WardropeSqlHelper;
import com.kirill.kochnev.homewardrope.ui.adapters.base.BaseHolder;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import java.util.Date;

import static com.kirill.kochnev.homewardrope.db.tables.LooksTable.LOOK_FULL_IMAGE_PATH;
import static com.kirill.kochnev.homewardrope.db.tables.LooksTable.LOOK_ICON_IMAGE_PATH;
import static com.kirill.kochnev.homewardrope.db.tables.LooksTable.LOOK_NAME;
import static com.kirill.kochnev.homewardrope.db.tables.LooksTable.LOOKS_TABLE;
import static com.kirill.kochnev.homewardrope.db.tables.LooksTable.LOOK_TAG;

/**
 * Created by Kirill Kochnev on 12.02.17.
 */

@StorIOSQLiteType(table = LOOKS_TABLE)
public class Look implements IDbModel {

    @StorIOSQLiteColumn(key = true, name = BaseColumns._ID)
    Long _id;

    @StorIOSQLiteColumn(name = WardropeSqlHelper.CREATION_DATE)
    String creationDate;

    @StorIOSQLiteColumn(name = LOOK_NAME)
    String name;

    @StorIOSQLiteColumn(name = LOOK_TAG)
    String tag;

    @StorIOSQLiteColumn(name = LOOK_FULL_IMAGE_PATH)
    String fullImagePath;

    @StorIOSQLiteColumn(name = LOOK_ICON_IMAGE_PATH)
    String iconImagePath;

    public Look() {
        this.creationDate = new Date().toString();
    }

    public Look(long id) {
        this._id = id;
        this.creationDate = new Date().toString();
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFullImagePath() {
        return fullImagePath;
    }

    public void setFullImagePath(String fullImagePath) {
        this.fullImagePath = fullImagePath;
    }

    public String getIconImagePath() {
        return iconImagePath;
    }

    public void setIconImagePath(String iconImagePath) {
        this.iconImagePath = iconImagePath;
    }

    @Override
    public Long getId() {
        return _id;
    }

    public void setId(Long _id) {
        this._id = _id;
    }

    @Override
    public void inflateHolder(BaseHolder holder) {

    }


}
