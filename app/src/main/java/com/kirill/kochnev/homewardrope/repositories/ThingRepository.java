package com.kirill.kochnev.homewardrope.repositories;

import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.db.models.ThingsWardropes;
import com.kirill.kochnev.homewardrope.db.tables.ThingsTable;
import com.kirill.kochnev.homewardrope.db.tables.ThingsWardropesTable;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractThingRepository;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.HashSet;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kirill on 30.03.17.
 */

public class ThingRepository extends AbstractThingRepository {

    public ThingRepository(StorIOSQLite storIOSQLite) {
        super(storIOSQLite);
    }


    @Override
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
    public Single<Boolean> deletItem(Thing model) {
        return super.deletItem(model).map(b -> {
            storIOSQLite.delete().byQuery(DeleteQuery.builder()
                    .table(ThingsWardropesTable.THINGS_WARDROPES_TABLE)
                    .where(ThingsWardropesTable.THINGS_WARDROPES_THING_ID + "=?")
                    .whereArgs(model.getId()).build())
                    .prepare()
                    .executeAsBlocking();
            return b;
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
