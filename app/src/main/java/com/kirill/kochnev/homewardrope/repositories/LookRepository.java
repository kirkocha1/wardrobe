package com.kirill.kochnev.homewardrope.repositories;

import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.db.models.LooksThings;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractLookRepository;
import com.kirill.kochnev.homewardrope.repositories.utils.ISpecification;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by kirill on 21.04.17.
 */

public class LookRepository extends AbstractLookRepository {

    public LookRepository(StorIOSQLite storIOSQLite) {
        super(storIOSQLite);
    }

    @Override
    public Single<Boolean> putItem(Look model) {
        return Single.create(sub -> {
            if (model.getThingIds().size() != 0) {
                storIOSQLite.lowLevel().beginTransaction();
                storIOSQLite.put().object(model);
                for (Long thingId : model.getThingIds()) {
                    storIOSQLite.put().object(new LooksThings(model.getId(), thingId));
                }
                storIOSQLite.lowLevel().endTransaction();
            } else {
                storIOSQLite.put().object(model);
            }
        });
    }

    @Override
    public Single<List<Look>> query(ISpecification filterSpecification) {
        return null;
    }
}
