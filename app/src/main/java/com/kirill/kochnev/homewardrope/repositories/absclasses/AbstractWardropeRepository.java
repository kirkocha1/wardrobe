package com.kirill.kochnev.homewardrope.repositories.absclasses;

import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import java.util.HashSet;

import io.reactivex.Single;

/**
 * Created by kirill on 30.03.17.
 */

public abstract class AbstractWardropeRepository extends AbstractRepository<Wardrope> {

    public AbstractWardropeRepository(StorIOSQLite storIOSQLite) {
        super(storIOSQLite);
    }

    public abstract Single<PutResult> putWardropeWithThings(Wardrope wardrope, HashSet<Long> thingIds);

}
