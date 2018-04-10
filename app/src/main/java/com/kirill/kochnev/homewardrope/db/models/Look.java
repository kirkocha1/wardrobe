package com.kirill.kochnev.homewardrope.db.models;

import com.kirill.kochnev.homewardrope.db.WardrobeSqlHelper;
import com.kirill.kochnev.homewardrope.presentation.ui.adapters.base.BaseHolder;
import com.kirill.kochnev.homewardrope.presentation.ui.adapters.holders.LookHolder;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteColumn;
import com.pushtorefresh.storio.sqlite.annotations.StorIOSQLiteType;

import java.util.Date;
import java.util.HashSet;

import static com.kirill.kochnev.homewardrope.db.tables.LooksTable.LOOKS_TABLE;
import static com.kirill.kochnev.homewardrope.db.tables.LooksTable.LOOK_FULL_IMAGE_PATH;
import static com.kirill.kochnev.homewardrope.db.tables.LooksTable.LOOK_ICON_IMAGE_PATH;
import static com.kirill.kochnev.homewardrope.db.tables.LooksTable.LOOK_NAME;
import static com.kirill.kochnev.homewardrope.db.tables.LooksTable.LOOK_TAG;
import static com.kirill.kochnev.homewardrope.db.tables.LooksTable.LOOK_WARDROBE_ID;

/**
 * Created by Kirill Kochnev on 12.02.17.
 */

@StorIOSQLiteType(table = LOOKS_TABLE)
public class Look extends BitmapModel implements IDbModel {

    @StorIOSQLiteColumn(key = true, name = _ID)
    Long _id;

    @StorIOSQLiteColumn(name = WardrobeSqlHelper.CREATION_DATE)
    String creationDate;

    @StorIOSQLiteColumn(name = LOOK_NAME)
    String name;

    @StorIOSQLiteColumn(name = LOOK_TAG)
    String tag;

    @StorIOSQLiteColumn(name = LOOK_FULL_IMAGE_PATH)
    String fullImagePath;

    @StorIOSQLiteColumn(name = LOOK_ICON_IMAGE_PATH)
    String iconImagePath;

    @StorIOSQLiteColumn(name = LOOK_WARDROBE_ID)
    Long wardrobeId;


    private HashSet<Long> thingIds;

    public Look() {
        thingIds = new HashSet<>();
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

    public HashSet<Long> getThingIds() {
        return thingIds;
    }

    public void setThingIds(HashSet<Long> thingIds) {
        this.thingIds = thingIds;
    }

    @Override
    public void inflateHolder(BaseHolder holder) {
        ((LookHolder) holder).item.setName(name);
        ((LookHolder) holder).item.setImage(iconImagePath);
    }

    public Long getWardrobeId() {
        return wardrobeId;
    }

    public void setWardrobeId(Long wardrobeId) {
        this.wardrobeId = wardrobeId;
    }

}
