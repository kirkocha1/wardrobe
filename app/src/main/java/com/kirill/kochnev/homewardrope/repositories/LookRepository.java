package com.kirill.kochnev.homewardrope.repositories;

import android.provider.BaseColumns;

import com.kirill.kochnev.homewardrope.db.RepoResult;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.db.models.LooksThings;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.db.tables.LooksTable;
import com.kirill.kochnev.homewardrope.db.tables.WardropeTable;
import com.kirill.kochnev.homewardrope.db.tables.manytomany.LooksThingsTable;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractRepository;
import com.kirill.kochnev.homewardrope.repositories.utils.ISpecification;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.get.PreparedGetListOfObjects;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by kirill on 21.04.17.
 */

public class LookRepository extends AbstractRepository<Look> {

    @Inject
    public LookRepository(StorIOSQLite storIOSQLite) {
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
    public Single<RepoResult> putItem(Look model) {
        return Single.create(sub -> {
            PutResult result;
            storIOSQLite.lowLevel().beginTransaction();
            try {
                if (model.getId() != null) {
                    decreaseWardropeLookCount(model.getId());
                    storIOSQLite.delete().byQuery(DeleteQuery.builder()
                            .table(LooksThingsTable.LOOKS_THINGS_TABLE)
                            .where(LooksThingsTable.LOOKS_THINGS_LOOK_ID + "=?")
                            .whereArgs(model.getId())
                            .build())
                            .prepare()
                            .executeAsBlocking();
                }
                result = storIOSQLite.put().object(model).prepare().executeAsBlocking();
                if (model.getWardropeId() != null) {
                    increaseWardropeLookCount(model.getWardropeId());
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
        }).flatMap(res -> Single.create(subscriber -> {
                    PutResult result = (PutResult) res;
                    subscriber.onSuccess(new RepoResult(result.wasInserted() ? result.insertedId() : model.getId(), result.wasInserted()));
                })
        );
    }

    private void decreaseWardropeLookCount(long lookId) {
        Look oldLook = storIOSQLite.get().object(Look.class).withQuery(Query.builder()
                .table(LooksTable.LOOKS_TABLE)
                .where(LooksTable._ID + "=?")
                .whereArgs(lookId).build()).prepare().executeAsBlocking();
        if (oldLook != null && oldLook.getWardropeId() != null) {
            Wardrope wardrope = getLookWardrope(oldLook.getWardropeId());
            if (wardrope != null) {
                wardrope.setLooksCount(wardrope.getLooksCount() - 1);
                storIOSQLite.put().object(wardrope).prepare().executeAsBlocking();
            }
        }
    }

    private void increaseWardropeLookCount(long wardrobeId) {
        Wardrope wardrope = getLookWardrope(wardrobeId);
        if (wardrope != null) {
            wardrope.setLooksCount(wardrope.getLooksCount() + 1);
            storIOSQLite.put().object(wardrope).prepare().executeAsBlocking();
        }
    }

    private Wardrope getLookWardrope(Long id) {
        return storIOSQLite.get().object(Wardrope.class).withQuery(Query.builder().table(WardropeTable.WARDROPE_TABLE)
                .where(BaseColumns._ID + " = ?").whereArgs(id + "")
                .build())
                .prepare()
                .executeAsBlocking();
    }

    @Override
    public Single<DeleteResult> deleteItem(Look model) {
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
