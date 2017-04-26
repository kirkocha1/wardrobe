package com.kirill.kochnev.homewardrope.repositories.utils;

import android.util.Log;

import com.kirill.kochnev.homewardrope.db.WardropeSqlHelper;
import com.kirill.kochnev.homewardrope.db.tables.ThingsTable;
import com.kirill.kochnev.homewardrope.db.tables.manytomany.ThingsWardropesTable;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

import static com.kirill.kochnev.homewardrope.AppConstants.LIMIT;

/**
 * Created by kirill on 26.04.17.
 */

public class ThingsByWardropeSpecification implements ISpecification {
    public static final String TAG = "ThingsByWardrope";
    private long id;
    private long wardropeId;

    public ThingsByWardropeSpecification(long id, long wardropeId) {
        this.id = id;
        this.wardropeId = wardropeId;
    }

    @Override
    public boolean isRow() {
        return false;
    }

    private String queryThings(long id, long wardropeId) {
        String result;

        String select = ThingsTable.THINGS_TABLE + "." + ThingsTable._ID + ", "
                + ThingsTable.THINGS_TABLE + "." + WardropeSqlHelper.CREATION_DATE + ", "
                + ThingsTable.THINGS_TABLE + "." + ThingsTable.THING_NAME + ", "
                + ThingsTable.THINGS_TABLE + "." + ThingsTable.THING_TAG + ", "
                + ThingsTable.THINGS_TABLE + "." + ThingsTable.THING_FULL_IMAGE_PATH + ", "
                + ThingsTable.THINGS_TABLE + "." + ThingsTable.THING_ICON_IMAGE_PATH;

        String join = wardropeId != -1 ? "JOIN " + ThingsWardropesTable.THINGS_WARDROPES_TABLE +
                " ON " + ThingsWardropesTable.THINGS_WARDROPES_THING_ID + " = " + ThingsTable.THINGS_TABLE + "." + ThingsTable._ID : "";

        String where = wardropeId != -1 ? "WHERE " + ThingsWardropesTable.THINGS_WARDROPES_TABLE + "."
                + ThingsWardropesTable.THINGS_WARDROPES_WARDROPES_ID + " = " + wardropeId : "";
        if (id == -1) {
            result = "SELECT " + select + " FROM " + ThingsTable.THINGS_TABLE + " " + join + " " + where +
                    " ORDER BY " + ThingsTable.THINGS_TABLE + "." + ThingsTable._ID +
                    " LIMIT " + LIMIT;
        } else {
            result = "SELECT " + select + " FROM " + ThingsTable.THINGS_TABLE + " " + join + " " + where +
                    " AND " + ThingsTable.THINGS_TABLE + "." + ThingsTable._ID + " > " + id +
                    " ORDER BY " + ThingsTable.THINGS_TABLE + "." + ThingsTable._ID +
                    " LIMIT " + LIMIT;
        }
        Log.d(TAG, result);
        return result;
    }

    @Override
    public Query.CompleteBuilder getQueryStatement() {
        return null;
    }

    @Override
    public RawQuery.CompleteBuilder getRawQueryStatement() {
        return RawQuery.builder().query(queryThings(id, wardropeId));
    }
}