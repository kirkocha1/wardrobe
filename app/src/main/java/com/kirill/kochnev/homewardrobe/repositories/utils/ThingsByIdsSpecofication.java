package com.kirill.kochnev.homewardrobe.repositories.utils;

import com.kirill.kochnev.homewardrobe.db.tables.ThingsTable;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

import java.util.HashSet;

/**
 * Created by kirill on 27.04.17.
 */

public class ThingsByIdsSpecofication implements ISpecification {

    private HashSet<Long> thingIds;

    public ThingsByIdsSpecofication(HashSet<Long> thingIds) {
        this.thingIds = thingIds;
    }

    private String constructWhere() {
        StringBuilder result = null;
        for (Long id : thingIds) {
            if (result == null) {
                result = new StringBuilder();
                result.append(ThingsTable._ID + "=" + id);
            } else {
                result.append(" OR " + " " + ThingsTable._ID + "=" + id);
            }
        }
        return result.toString();
    }

    @Override
    public boolean isRow() {
        return false;
    }

    @Override
    public Query.CompleteBuilder getQueryStatement() {
        return Query.builder().table(ThingsTable.THINGS_TABLE).where(constructWhere());
    }

    @Override
    public RawQuery.CompleteBuilder getRawQueryStatement() {
        return null;
    }
}
