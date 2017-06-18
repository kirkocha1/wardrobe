package com.kirill.kochnev.homewardrope.repositories.utils;

import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.db.tables.LooksTable;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

/**
 * Created by Kirill Kochnev on 15.05.17.
 */

public class LooksByWardropeSpecification implements ISpecification {

    private long id;
    private long filterId;

    public LooksByWardropeSpecification(long id, long wardropeId) {
        this.filterId = wardropeId;
        this.id = id;
    }

    @Override
    public boolean isRow() {
        return false;
    }

    @Override
    public Query.CompleteBuilder getQueryStatement() {
        Query.CompleteBuilder result;
        if (id != AppConstants.DEFAULT_ID) {
            result = Query.builder().table(LooksTable.LOOKS_TABLE)
                    .where(LooksTable.LOOK_WARDROPE_ID + " =? AND " + LooksTable._ID + " > " + " = ? " + "LIMIT " + AppConstants.LIMIT)
                    .whereArgs(filterId, id);
        } else {
            result = Query.builder().table(LooksTable.LOOKS_TABLE).where(LooksTable.LOOK_WARDROPE_ID + "=?").whereArgs(filterId);
        }
        return result;
    }

    @Override
    public RawQuery.CompleteBuilder getRawQueryStatement() {
        return null;
    }
}
