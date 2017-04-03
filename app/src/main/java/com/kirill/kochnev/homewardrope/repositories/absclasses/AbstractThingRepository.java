package com.kirill.kochnev.homewardrope.repositories.absclasses;

import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

/**
 * Created by kirill on 30.03.17.
 */

public abstract class AbstractThingRepository extends AbstractRepository<Thing> {

    public AbstractThingRepository(StorIOSQLite storIOSQLite) {
        super(storIOSQLite);
    }


}
