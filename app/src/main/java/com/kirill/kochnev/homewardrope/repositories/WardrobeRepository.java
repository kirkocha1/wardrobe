package com.kirill.kochnev.homewardrope.repositories;

import com.kirill.kochnev.homewardrope.db.RepoResult;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.db.models.ThingsWardropes;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.db.tables.LooksTable;
import com.kirill.kochnev.homewardrope.db.tables.WardropeTable;
import com.kirill.kochnev.homewardrope.db.tables.manytomany.ThingsWardropesTable;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractRepository;
import com.kirill.kochnev.homewardrope.repositories.utils.ISpecification;
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

public class WardrobeRepository extends AbstractRepository<Wardrope> {

    @Inject
    public WardrobeRepository(StorIOSQLite storIOSQLite) {
        super(storIOSQLite);
    }

    @Override
    public Single<RepoResult> putItem(Wardrope wardrope) {
        Single<PutResult> resultSingle = Single.create(sub -> {
            storIOSQLite.lowLevel().beginTransaction();
            try {
                if (wardrope.getId() != null) {
                    storIOSQLite.delete().byQuery(DeleteQuery.builder()
                            .table(ThingsWardropesTable.THINGS_WARDROPES_TABLE)
                            .where(ThingsWardropesTable.THINGS_WARDROPES_WARDROPES_ID + "=?")
                            .whereArgs(wardrope.getId())
                            .build())
                            .prepare()
                            .executeAsBlocking();

                    storIOSQLite.lowLevel().executeSQL(RawQuery.builder()
                            .query(LooksTable.dropWardropeId(wardrope.getId()))
                            .build());
                }

                PutResult result = storIOSQLite.put().object(wardrope).prepare().executeAsBlocking();
                Long wardropeId = result.wasInserted() ? result.insertedId() : wardrope.getId();
                if (wardropeId == null) {
                    sub.onError(new Exception("wardropeId is null"));
                } else {
                    storIOSQLite.lowLevel().executeSQL(RawQuery.builder()
                            .query(LooksTable.updateWardropeId(wardrope.getLookIdsString(), wardropeId))
                            .build());

                    List<ThingsWardropes> thingsWardropes = new ArrayList<>();
                    for (Long id : wardrope.getThingIds()) {
                        thingsWardropes.add(new ThingsWardropes(wardropeId, id));
                    }
                    storIOSQLite.put().objects(thingsWardropes).prepare().executeAsBlocking();
                    storIOSQLite.lowLevel().setTransactionSuccessful();
                    sub.onSuccess(result);

                }
            } catch (Exception e) {
                sub.onError(new Exception("wardrope wasn't inserted or updated"));
            } finally {
                storIOSQLite.lowLevel().endTransaction();
            }
        });
        return resultSingle.map(result -> new RepoResult(result.wasInserted() ? result.insertedId() : wardrope.getId(), result.wasInserted()));
    }

    //Get wardrobe and fill it with isd of things and looks
    @Override
    public Single<Wardrope> getItem(long id) {
        return super.getItem(id).map(wardrope -> {
            List<ThingsWardropes> thingsWardropes = storIOSQLite.get().listOfObjects(ThingsWardropes.class)
                    .withQuery(Query.builder().table(ThingsWardropesTable.THINGS_WARDROPES_TABLE).build())
                    .prepare()
                    .executeAsBlocking();
            List<Look> looks = storIOSQLite.get().listOfObjects(Look.class)
                    .withQuery(Query.builder().table(LooksTable.LOOKS_TABLE)
                            .where(LooksTable.LOOK_WARDROPE_ID + "=?")
                            .whereArgs(id + "").build())
                    .prepare()
                    .executeAsBlocking();

            wardrope.setLookIds(new HashSet<>());
            wardrope.setThingIds(new HashSet<>());
            for (Look look : looks) {
                wardrope.getLookIds().add(look.getId());
            }

            for (ThingsWardropes thingsWardrope : thingsWardropes) {
                if (id == thingsWardrope.getWardropeId()) {
                    wardrope.getThingIds().add(thingsWardrope.getThingId());
                }
            }
            wardrope.setLooksCount(looks.size());
            wardrope.setThingsCount(wardrope.getThingIds().size());
            return wardrope;
        });
    }

    @Override
    public Single<DeleteResult> deletItem(Wardrope model) {
        return Single.create(sub -> {
            storIOSQLite.lowLevel().beginTransaction();
            try {
                storIOSQLite.delete().byQuery(DeleteQuery.builder()
                        .table(ThingsWardropesTable.THINGS_WARDROPES_TABLE)
                        .where(ThingsWardropesTable.THINGS_WARDROPES_WARDROPES_ID + "=?")
                        .whereArgs(model.getId()).build()).prepare().executeAsBlocking();

                storIOSQLite.lowLevel().executeSQL(RawQuery.builder()
                        .query(LooksTable.dropWardropeId(model.getId()))
                        .build());

                DeleteResult result = storIOSQLite.delete().object(model).prepare().executeAsBlocking();
                storIOSQLite.lowLevel().setTransactionSuccessful();
                sub.onSuccess(result);
            } catch (Exception e) {
                sub.onError(new Exception("wardrope wasn't deleted"));
            } finally {
                storIOSQLite.lowLevel().endTransaction();
            }
        });
    }

    @Override
    public Single<List<Wardrope>> query(ISpecification filterSpecification) {
        return null;
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
