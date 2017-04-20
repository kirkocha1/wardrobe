package com.kirill.kochnev.homewardrope.repositories;

import com.kirill.kochnev.homewardrope.db.models.ThingsWardropes;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.db.tables.ThingsWardropesTable;
import com.kirill.kochnev.homewardrope.db.tables.WardropeTable;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractWardropeRepository;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio.sqlite.queries.Query;

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
                if (wardrope.getId() != null) {
                    storIOSQLite.delete().byQuery(DeleteQuery.builder()
                            .table(ThingsWardropesTable.THINGS_WARDROPES_TABLE)
                            .where(ThingsWardropesTable.THINGS_WARDROPES_WARDROPES_ID + "=?")
                            .whereArgs(wardrope.getId())
                            .build())
                            .prepare()
                            .executeAsBlocking();
                }
                PutResult result = storIOSQLite.put().object(wardrope).prepare().executeAsBlocking();
                Long wardropeId = result.wasInserted() ? result.insertedId() : wardrope.getId();
                List<ThingsWardropes> thingsWardropes = new ArrayList<>();
                for (Long id : thingIds) {
                    thingsWardropes.add(new ThingsWardropes(wardropeId, id));
                }
                storIOSQLite.put().objects(thingsWardropes).prepare().executeAsBlocking();
                sub.onSuccess(new Object());

            } catch (Exception ex) {
                sub.onError(ex);
            }
        });
    }

    @Override
    public Single<Wardrope> getItem(long id) {
        return super.getItem(id).map(wardrope -> {
            List<ThingsWardropes> thingsWardropes = storIOSQLite.get().listOfObjects(ThingsWardropes.class)
                    .withQuery(Query.builder().table(ThingsWardropesTable.THINGS_WARDROPES_TABLE).build())
                    .prepare()
                    .executeAsBlocking();
            wardrope.setThingIds(new HashSet<>());
            for (ThingsWardropes thingsWardrope : thingsWardropes) {
                if (id == thingsWardrope.getWardropeId()) {
                    wardrope.getThingIds().add(thingsWardrope.getThingId());
                }
            }
            return wardrope;
        });
    }

    @Override
    public Single<Boolean> deletItem(Wardrope model) {
        return Single.create(sub -> {
            try {
                storIOSQLite.delete().byQuery(DeleteQuery.builder()
                        .table(ThingsWardropesTable.THINGS_WARDROPES_TABLE)
                        .where(ThingsWardropesTable.THINGS_WARDROPES_WARDROPES_ID + "=?")
                        .whereArgs(model.getId()).build()).prepare().executeAsBlocking();
                DeleteResult result = storIOSQLite.delete().object(model).prepare().executeAsBlocking();
                if (result.numberOfRowsDeleted() == 1) {
                    sub.onSuccess(true);
                } else {
                    sub.onSuccess(false);
                }
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
