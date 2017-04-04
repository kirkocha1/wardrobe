package com.kirill.kochnev.homewardrope.repositories;

import com.kirill.kochnev.homewardrope.db.models.ThingsWardropes;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.db.tables.WardropeTable;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractWardropeRepository;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by kirill on 30.03.17.
 */

public class WardropeRepository extends AbstractWardropeRepository {

    public WardropeRepository(StorIOSQLite storIOSQLite) {
        super(storIOSQLite);
    }

    @Override
    public Single<Object> putWardropeWithThings(Wardrope wardrope, HashSet<Long> thingIds) {
        return Single.create(sub -> {
            try {
                PutResult result = storIOSQLite.put().object(wardrope).prepare().executeAsBlocking();
                List<ThingsWardropes> thingsWardropes = new ArrayList<>();
                Long wardropeId = result.insertedId();
                if (wardropeId != null) {
                    for (Long id : thingIds) {
                        thingsWardropes.add(new ThingsWardropes(wardropeId, id));
                    }
                    storIOSQLite.put().objects(thingsWardropes).prepare().executeAsBlocking();
                } else {
                    throw new Exception("wardrope wasn't put");
                }

                sub.onSuccess(new Object());
            } catch (Exception ex) {
                sub.onError(ex);
            }
        });
    }

    @Override
    public Class<Wardrope> getEntityClass() {
        return Wardrope.class;
    }

    @Override
    public String getTableName() {
        return WardropeTable.WARDROPE_TABLE;
    }
}
