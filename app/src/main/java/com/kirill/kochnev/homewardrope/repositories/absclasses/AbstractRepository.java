package com.kirill.kochnev.homewardrope.repositories.absclasses;

import android.provider.BaseColumns;

import com.kirill.kochnev.homewardrope.repositories.interfaces.IRepository;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by kirill on 30.03.17.
 */

//Abstract repository for common CRUD operation
public abstract class AbstractRepository<M> implements IRepository<M> {

    private StorIOSQLite storIOSQLite;

    public AbstractRepository(StorIOSQLite storIOSQLite) {
        this.storIOSQLite = storIOSQLite;
    }

    @Override
    public Single<List<M>> getNextList(long id) {
        return Single.create(sub -> {
            try {
                List<M> models = null;
                if (id != -1) {
                    models = storIOSQLite.get().listOfObjects(getEntityClass()).withQuery(Query.builder().table(getTableName())
                            .where(BaseColumns._ID + " = ?")
                            .whereArgs(id + "")
                            .limit(LIMIT)
                            .build()).prepare().executeAsBlocking();
                } else {
                    models = storIOSQLite.get().listOfObjects(getEntityClass()).withQuery(Query.builder().table(getTableName())
                            .limit(LIMIT)
                            .build()).prepare().executeAsBlocking();
                }
                sub.onSuccess(new ArrayList<>(models));

            } catch (Exception ex) {
                sub.onError(new Exception("nothing to load"));
            }
        });
    }

    public abstract Class<M> getEntityClass();

    public abstract String getTableName();

    @Override
    public Single<Boolean> putItem(M model) {
        return Single.create(sub -> {
            try {
                storIOSQLite.put().object(model).prepare().executeAsBlocking();
                sub.onSuccess(true);
            } catch (Exception ex) {
                sub.onError(ex);
            }
        });
    }

    @Override
    public Single<M> getItem(long id) {
        return Single.create(sub -> {
            M thing = storIOSQLite.get().object(getEntityClass()).withQuery(Query.builder().table(getTableName())
                    .where(BaseColumns._ID + " = ?").whereArgs(id + "")
                    .build())
                    .prepare()
                    .executeAsBlocking();
            if (thing != null) {
                sub.onSuccess(thing);
            } else {
                sub.onError(new Exception("no thing with this id"));
            }
        });
    }


    public Single<Boolean> deletItem(M model) {
        return Single.create(sub -> {
            try {
                storIOSQLite.delete().object(model).prepare().executeAsBlocking();
                sub.onSuccess(true);
            } catch (Exception ex) {
                sub.onError(ex);
            }
        });
    }

}
