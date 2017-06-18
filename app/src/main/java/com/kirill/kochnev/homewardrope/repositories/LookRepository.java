package com.kirill.kochnev.homewardrope.repositories;

import android.provider.BaseColumns;

import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.db.models.LooksThings;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.db.tables.WardropeTable;
import com.kirill.kochnev.homewardrope.db.tables.manytomany.LooksThingsTable;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractLookRepository;
import com.kirill.kochnev.homewardrope.repositories.utils.ISpecification;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.get.PreparedGetListOfObjects;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio.sqlite.queries.Query;

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
    public Single<List<Look>> query(ISpecification filterSpecification) {
        return Single.create(sub -> {
            List<Look> models;
            PreparedGetListOfObjects.Builder<Look> builder = storIOSQLite.get().listOfObjects(getEntityClass());
            models = builder.withQuery(filterSpecification.getQueryStatement()
                    .build()).prepare().executeAsBlocking();
            sub.onSuccess(new ArrayList<>(models));
        });
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
                Wardrope wardrope = getLookWardrope(model.getWardropeId());
                if (wardrope != null) {
                    wardrope.setLooksCount(wardrope.getLooksCount() + 1);
                    storIOSQLite.put().object(wardrope).prepare().executeAsBlocking();
                }
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

    private Wardrope getLookWardrope(long id) {
        return storIOSQLite.get().object(Wardrope.class).withQuery(Query.builder().table(WardropeTable.WARDROPE_TABLE)
                .where(BaseColumns._ID + " = ?").whereArgs(id + "")
                .build())
                .prepare()
                .executeAsBlocking();
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
                Wardrope wardrope = getLookWardrope(model.getWardropeId());
                if (wardrope != null) {
                    wardrope.setLooksCount(wardrope.getLooksCount() - 1);
                    storIOSQLite.put().object(wardrope).prepare().executeAsBlocking();
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
