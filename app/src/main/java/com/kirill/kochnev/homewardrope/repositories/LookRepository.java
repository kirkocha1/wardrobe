package com.kirill.kochnev.homewardrope.repositories;

import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.db.models.LooksThings;
import com.kirill.kochnev.homewardrope.db.tables.manytomany.LooksThingsTable;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractLookRepository;
import com.kirill.kochnev.homewardrope.repositories.utils.ISpecification;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;

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
                PutResult result = storIOSQLite.put().object(model).prepare().executeAsBlocking();
                model.setId(result.insertedId());
                for (Long thingId : model.getThingIds()) {
                    storIOSQLite.put().object(new LooksThings(model.getId(), thingId)).prepare().executeAsBlocking();
                }
                storIOSQLite.lowLevel().setTransactionSuccessful();
                storIOSQLite.lowLevel().endTransaction();
            } else {
                storIOSQLite.put().object(model);
            }
            sub.onSuccess(true);
        });
    }

    @Override
    public Single<Boolean> deletItem(Look model) {
        return super.deletItem(model).map(bool -> {
            if (model.getThingIds().size() != 0) {
                storIOSQLite.lowLevel().beginTransaction();
                for (Long thingId : model.getThingIds()) {
                    storIOSQLite.delete().byQuery(DeleteQuery.builder()
                            .table(LooksThingsTable.LOOKS_THINGS_TABLE).where(LooksThingsTable._ID + "=?")
                            .whereArgs(thingId).build())
                            .prepare().executeAsBlocking();
                }
                storIOSQLite.lowLevel().setTransactionSuccessful();
                storIOSQLite.lowLevel().endTransaction();
            }
            return bool;
        });
    }


}
