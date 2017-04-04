package com.kirill.kochnev.homewardrope.repositories;

import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.db.tables.ThingsTable;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractThingRepository;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

/**
 * Created by kirill on 30.03.17.
 */

public class ThingRepository extends AbstractThingRepository {

    public ThingRepository(StorIOSQLite storIOSQLite) {
        super(storIOSQLite);
    }

    @Override
    public Class<Thing> getEntityClass() {
        return Thing.class;
    }

    @Override
    public String getTableName() {
        return ThingsTable.THINGS_TABLE;
    }
}
