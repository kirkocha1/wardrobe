package com.kirill.kochnev.homewardrobe.repositories;

import com.kirill.kochnev.homewardrobe.db.RepoResult;
import com.kirill.kochnev.homewardrobe.db.models.Look;
import com.kirill.kochnev.homewardrobe.db.models.ThingsWardrobes;
import com.kirill.kochnev.homewardrobe.db.models.Wardrobe;
import com.kirill.kochnev.homewardrobe.db.tables.LooksTable;
import com.kirill.kochnev.homewardrobe.db.tables.WardrobeTable;
import com.kirill.kochnev.homewardrobe.db.tables.manytomany.ThingsWardropesTable;
import com.kirill.kochnev.homewardrobe.repositories.absclasses.AbstractRepository;
import com.kirill.kochnev.homewardrobe.repositories.utils.ISpecification;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery;
import com.pushtorefresh.storio.sqlite.queries.Query;
import com.pushtorefresh.storio.sqlite.queries.RawQuery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by kirill on 30.03.17.
 */

public class WardrobeRepository extends AbstractRepository<Wardrobe> {

    @Inject
    WardrobeRepository(StorIOSQLite storIOSQLite) {
        super(storIOSQLite);
    }

    @Override
    public Single<RepoResult> putItem(Wardrobe wardrobe) {
        Single<PutResult> resultSingle = Single.create(sub -> {
            storIOSQLite.lowLevel().beginTransaction();
            try {
                if (wardrobe.getId() != null) {
                    storIOSQLite.delete().byQuery(DeleteQuery.builder()
                            .table(ThingsWardropesTable.THINGS_WARDROBES_TABLE)
                            .where(ThingsWardropesTable.THINGS_WARDROBES_WARDROBES_ID + "=?")
                            .whereArgs(wardrobe.getId())
                            .build())
                            .prepare()
                            .executeAsBlocking();

                    storIOSQLite.lowLevel().executeSQL(RawQuery.builder()
                            .query(LooksTable.dropWardrobeId(wardrobe.getId()))
                            .build());
                }

                PutResult result = storIOSQLite.put().object(wardrobe).prepare().executeAsBlocking();
                Long wardropeId = result.wasInserted() ? result.insertedId() : wardrobe.getId();
                if (wardropeId == null) {
                    sub.onError(new Exception("wardropeId is null"));
                } else {
                    storIOSQLite.lowLevel().executeSQL(RawQuery.builder()
                            .query(LooksTable.updateWardrobeId(wardrobe.getLookIdsString(), wardropeId))
                            .build());

                    List<ThingsWardrobes> thingsWardropes = new ArrayList<>();
                    for (Long id : wardrobe.getThingIds()) {
                        thingsWardropes.add(new ThingsWardrobes(wardropeId, id));
                    }
                    storIOSQLite.put().objects(thingsWardropes).prepare().executeAsBlocking();
                    storIOSQLite.lowLevel().setTransactionSuccessful();
                    sub.onSuccess(result);

                }
            } catch (Exception e) {
                sub.onError(new Exception("wardrobe wasn't inserted or updated"));
            } finally {
                storIOSQLite.lowLevel().endTransaction();
            }
        });
        return resultSingle.map(result -> new RepoResult(result.wasInserted() ? result.insertedId() : wardrobe.getId(), result.wasInserted()));
    }

    //Get wardrobe and fill it with ids of things and looks
    @Override
    public Single<Wardrobe> getItem(long id) {
        return super.getItem(id).map(wardrobe -> {
            List<ThingsWardrobes> thingsWardrobes = storIOSQLite.get().listOfObjects(ThingsWardrobes.class)
                    .withQuery(Query.builder().table(ThingsWardropesTable.THINGS_WARDROBES_TABLE).build())
                    .prepare()
                    .executeAsBlocking();
            List<Look> looks = storIOSQLite.get().listOfObjects(Look.class)
                    .withQuery(Query.builder().table(LooksTable.LOOKS_TABLE)
                            .where(LooksTable.LOOK_WARDROBE_ID + "=?")
                            .whereArgs(id + "").build())
                    .prepare()
                    .executeAsBlocking();

            wardrobe.setLookIds(new HashSet<>());
            wardrobe.setThingIds(new HashSet<>());
            for (Look look : looks) {
                wardrobe.getLookIds().add(look.getId());
            }

            for (ThingsWardrobes thingsWardrope : thingsWardrobes) {
                if (id == thingsWardrope.getWardrobeId()) {
                    wardrobe.getThingIds().add(thingsWardrope.getThingId());
                }
            }
            wardrobe.setLooksCount(looks.size());
            wardrobe.setThingsCount(wardrobe.getThingIds().size());
            return wardrobe;
        });
    }

    @Override
    public Single<DeleteResult> deleteItem(Wardrobe model) {
        return Single.create(sub -> {
            storIOSQLite.lowLevel().beginTransaction();
            try {
                storIOSQLite.delete().byQuery(DeleteQuery.builder()
                        .table(ThingsWardropesTable.THINGS_WARDROBES_TABLE)
                        .where(ThingsWardropesTable.THINGS_WARDROBES_WARDROBES_ID + "=?")
                        .whereArgs(model.getId()).build()).prepare().executeAsBlocking();

                storIOSQLite.lowLevel().executeSQL(RawQuery.builder()
                        .query(LooksTable.dropWardrobeId(model.getId()))
                        .build());

                DeleteResult result = storIOSQLite.delete().object(model).prepare().executeAsBlocking();
                storIOSQLite.lowLevel().setTransactionSuccessful();
                sub.onSuccess(result);
            } catch (Exception e) {
                sub.onError(new Exception("wardrobe wasn't deleted"));
            } finally {
                storIOSQLite.lowLevel().endTransaction();
            }
        });
    }

    @Override
    public Single<List<Wardrobe>> query(ISpecification filterSpecification) {
        return null;
    }

    @Override
    public Class<Wardrobe> getEntityClass() {
        return Wardrobe.class;
    }

    @Override
    public String getTableName() {
        return WardrobeTable.WARDROBE_TABLE;
    }
}
