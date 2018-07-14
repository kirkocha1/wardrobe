package com.kirill.kochnev.homewardrobe.repositories.utils;

import android.util.Log;

import com.kirill.kochnev.homewardrobe.AppConstants;
import com.kirill.kochnev.homewardrobe.db.tables.ThingsTable;
import com.kirill.kochnev.homewardrobe.db.tables.manytomany.ThingsWardropesTable;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

import static com.kirill.kochnev.homewardrobe.AppConstants.LIMIT;

/**
 * Created by kirill on 26.04.17.
 */

public class ThingsByWardrobeSpecification implements ISpecification {
    public static final String TAG = "ThingsByWardrope";
    private long id;
    private long wardrobeId;

    public ThingsByWardrobeSpecification(long id, long wardrobeId) {
        this.id = id;
        this.wardrobeId = wardrobeId;
    }

    @Override
    public boolean isRow() {
        return true;
    }

    private String queryThings(long id, long wardropeId) {
        String result = "";
        if (wardropeId == AppConstants.DEFAULT_ID) {
            return result;
        }

        String join = "JOIN " + ThingsWardropesTable.THINGS_WARDROBES_TABLE +
                " ON " + ThingsWardropesTable.THINGS_WARDROBES_THING_ID + " = " + ThingsTable.THINGS_TABLE + "." + ThingsTable._ID;

        String where = "WHERE " + ThingsWardropesTable.THINGS_WARDROBES_TABLE + "."
                + ThingsWardropesTable.THINGS_WARDROBES_WARDROBES_ID + " = " + wardropeId;
        if (id == -1) {
            result = "SELECT " + ThingsTable.THINGS_TABLE + ".*" + " FROM " + ThingsTable.THINGS_TABLE + " " + join + " " + where +
                    " ORDER BY " + ThingsTable.THINGS_TABLE + "." + ThingsTable._ID +
                    " LIMIT " + LIMIT;
        } else {
            result = "SELECT " + ThingsTable.THINGS_TABLE + ".*" + " FROM " + ThingsTable.THINGS_TABLE + " " + join + " " + where +
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
        return RawQuery.builder().query(queryThings(id, wardrobeId));
    }
}