package com.kirill.kochnev.homewardrope.repositories.absclasses;

import android.provider.BaseColumns;

import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.repositories.interfaces.IRepository;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static com.kirill.kochnev.homewardrope.AppConstants.LIMIT;

/**
 * Created by kirill on 30.03.17.
 */

//Abstract repository for common CRUD operation
public abstract class AbstractRepository<M> implements IRepository<M> {

    public StorIOSQLite storIOSQLite;

    public AbstractRepository(StorIOSQLite storIOSQLite) {
        this.storIOSQLite = storIOSQLite;
    }

    @Override
    public Single<List<M>> query() {
        return query(AppConstants.DEFAULT_ID);
    }

    @Override
    public Single<List<M>> query(long id) {
        return Single.create(sub -> {
            try {
                Query.CompleteBuilder builder = Query.builder().table(getTableName()).limit(LIMIT);
                if (id != -1) {
                    builder.where(BaseColumns._ID + " > ? ").whereArgs(id + "");
                }
                List<M> models = storIOSQLite.get().listOfObjects(getEntityClass()).withQuery(builder.build())
                        .prepare().executeAsBlocking();
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
