package com.kirill.kochnev.homewardrobe.repositories.utils;

import com.kirill.kochnev.homewardrobe.AppConstants;
import com.kirill.kochnev.homewardrobe.db.tables.LooksTable;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

/**
 * Created by Kirill Kochnev on 15.05.17.
 */

public class LooksByWardrobeSpecification implements ISpecification {

    private long id;
    private long filterId;

    public LooksByWardrobeSpecification(long id, long wardropeId) {
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
                    .where(LooksTable.LOOK_WARDROBE_ID + " =? AND " + LooksTable._ID + " > " + " = ? " + "LIMIT " + AppConstants.LIMIT)
                    .whereArgs(filterId, id);
        } else {
            result = Query.builder().table(LooksTable.LOOKS_TABLE).where(LooksTable.LOOK_WARDROBE_ID + "=?").whereArgs(filterId);
        }
        return result;
    }

    @Override
    public RawQuery.CompleteBuilder getRawQueryStatement() {
        return null;
    }
}
