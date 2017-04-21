package com.kirill.kochnev.homewardrope.repositories.absclasses;

import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.db.tables.LooksTable;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

/**
 * Created by kirill on 21.04.17.
 */

public class AbstractLookRepository extends AbstractRepository<Look>{

    public AbstractLookRepository(StorIOSQLite storIOSQLite) {
        super(storIOSQLite);
    }

    @Override
    public Class<Look> getEntityClass() {
        return Look.class;
    }

    @Override
    public String getTableName() {
        return LooksTable.LOOKS_TABLE;
    }
}
