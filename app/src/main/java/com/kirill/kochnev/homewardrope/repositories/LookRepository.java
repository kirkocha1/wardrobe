package com.kirill.kochnev.homewardrope.repositories;

import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.db.models.LooksThings;
import com.kirill.kochnev.homewardrope.db.tables.manytomany.LooksThingsTable;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractLookRepository;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;

import java.util.ArrayList;
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
    public Single<PutResult> putItem(Look model) {
        return Single.create(sub -> {
            PutResult result;
            storIOSQLite.lowLevel().beginTransaction();
            try {
                if (model.getId() != null) {
                    storIOSQLite.delete().byQuery(DeleteQuery.builder()
                            .table(LooksThingsTable.LOOKS_THINGS_TABLE)
                            .where(LooksThingsTable.LOOKS_THINGS_LOOK_ID + "=?")
                            .whereArgs(model.getId())
                            .build())
                            .prepare()
                            .executeAsBlocking();
                }
                result = storIOSQLite.put().object(model).prepare().executeAsBlocking();
                if (model.getThingIds().size() != 0) {
                    List<LooksThings> looksThings = new ArrayList<>();
                    model.setId(model.getId() != null ? model.getId() : result.insertedId());

                    for (Long thingId : model.getThingIds()) {
                        looksThings.add(new LooksThings(model.getId(), thingId));
                    }
                    storIOSQLite.put().objects(looksThings).prepare().executeAsBlocking();
                }
                storIOSQLite.lowLevel().setTransactionSuccessful();
                sub.onSuccess(result);
            } catch (Exception e) {
                sub.onError(new Exception("look wasn't inserted"));
            } finally {
                storIOSQLite.lowLevel().endTransaction();
            }
        });
    }

    @Override
    public Single<DeleteResult> deletItem(Look model) {
        return Single.create(sub -> {
            storIOSQLite.lowLevel().beginTransaction();
            try {
                if (model.getThingIds().size() != 0) {
                    for (Long thingId : model.getThingIds()) {
                        storIOSQLite.delete().byQuery(DeleteQuery.builder()
                                .table(LooksThingsTable.LOOKS_THINGS_TABLE).where(LooksThingsTable._ID + "=?")
                                .whereArgs(thingId).build())
                                .prepare().executeAsBlocking();
                    }
                }
                DeleteResult result = storIOSQLite.delete().object(model).prepare().executeAsBlocking();
                storIOSQLite.lowLevel().setTransactionSuccessful();
                sub.onSuccess(result);
            } catch (Exception e) {
                sub.onError(new Exception("look wasn't delete"));
            } finally {
                storIOSQLite.lowLevel().endTransaction();
            }
        });
    }
}
