package com.kirill.kochnev.homewardrope.repositories;

import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.db.models.ThingsWardropes;
import com.kirill.kochnev.homewardrope.db.tables.ThingsTable;
import com.kirill.kochnev.homewardrope.db.tables.manytomany.ThingsWardropesTable;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractRepository;
import com.kirill.kochnev.homewardrope.repositories.utils.ISpecification;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.get.PreparedGetListOfObjects;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kirill on 30.03.17.
 */

public class ThingRepository extends AbstractRepository<Thing> {

    public ThingRepository(StorIOSQLite storIOSQLite) {
        super(storIOSQLite);
    }

    public Single<HashSet<Long>> getWardropeThingIds(long wardropeId) {
        return Single.create(sub -> {
            try {
                List<ThingsWardropes> thingsWardropesList = storIOSQLite.get()
                        .listOfObjects(ThingsWardropes.class)
                        .withQuery(Query.builder().table(ThingsWardropesTable.THINGS_WARDROPES_TABLE)
                                .where(ThingsWardropesTable.THINGS_WARDROPES_WARDROPES_ID + "=?").whereArgs(wardropeId).build())
                        .prepare().executeAsBlocking();
                HashSet<Long> result = new HashSet<>();
                for (ThingsWardropes entity : thingsWardropesList) {
                    result.add(entity.getThingId());
                }
                sub.onSuccess(result);
            } catch (Exception ex) {
                sub.onError(ex);
            }

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(o -> (HashSet<Long>) o);
    }

    @Override
    public Single<DeleteResult> deletItem(Thing model) {
        return Single.create(sub -> {
            storIOSQLite.lowLevel().beginTransaction();
            try {
                storIOSQLite.delete().byQuery(DeleteQuery.builder()
                        .table(ThingsWardropesTable.THINGS_WARDROPES_TABLE)
                        .where(ThingsWardropesTable.THINGS_WARDROPES_THING_ID + "=?")
                        .whereArgs(model.getId()).build())
                        .prepare()
                        .executeAsBlocking();
                DeleteResult result = storIOSQLite.delete().object(model).prepare().executeAsBlocking();
                storIOSQLite.lowLevel().setTransactionSuccessful();
                sub.onSuccess(result);
            } catch (Exception e) {
                sub.onError(new Exception("thing wasn't delete"));
            } finally {
                storIOSQLite.lowLevel().endTransaction();
            }
        });
    }

    @Override
    public Single<List<Thing>> query(ISpecification filterSpecification) {
        return Single.create(sub -> {
            List<Thing> models;
            PreparedGetListOfObjects.Builder<Thing> builder = storIOSQLite.get().listOfObjects(getEntityClass());
            if (filterSpecification.isRow()) {
                models = builder.withQuery(filterSpecification.getRawQueryStatement()
                        .build()).prepare().executeAsBlocking();
            } else {
                models = builder.withQuery(filterSpecification.getQueryStatement()
                        .build()).prepare().executeAsBlocking();
            }
            sub.onSuccess(new ArrayList<>(models));
        });
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
