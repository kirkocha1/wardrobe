package com.kirill.kochnev.homewardrope.repositories.absclasses;

import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import java.util.HashSet;

import io.reactivex.Single;

/**
 * Created by kirill on 30.03.17.
 */

public abstract class AbstractThingRepository extends AbstractRepository<Thing> {

    public AbstractThingRepository(StorIOSQLite storIOSQLite) {
        super(storIOSQLite);
    }

    public abstract Single<HashSet<Long>> getWardropeThingIds(long wardropeId);


}
