package com.kirill.kochnev.homewardrope.repositories.absclasses;

import android.provider.BaseColumns;

import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.db.RepoResult;
import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.repositories.interfaces.IRepository;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.queries.Query;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static com.kirill.kochnev.homewardrope.AppConstants.LIMIT;

/**
 * Created by kirill on 30.03.17.
 */

//Abstract repository for common CRUD operation
public abstract class AbstractRepository<M extends IDbModel> implements IRepository<M> {

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
        return Single.fromCallable(() -> {
            Query.CompleteBuilder builder = Query.builder().table(getTableName()).limit(LIMIT);
            builder.where(BaseColumns._ID + " > ? ").whereArgs(id + "");
            List<M> entries = storIOSQLite
                    .get()
                    .listOfObjects(getEntityClass())
                    .withQuery(builder.build())
                    .prepare()
                    .executeAsBlocking();

            return new ArrayList<>(entries);
        });
    }

    public abstract Class<M> getEntityClass();

    public abstract String getTableName();

    @Override
    public Single<RepoResult> putItem(M model) {
        return Single.fromCallable(() -> storIOSQLite.put().object(model).prepare().executeAsBlocking())
                .map(res -> new RepoResult(res.wasInserted() ? res.insertedId() : model.getId(), res.wasInserted()));
    }

    @Override
    public Single<M> getItem(long id) {
        return Single.fromCallable(() ->
                storIOSQLite.get().object(getEntityClass()).withQuery(Query.builder().table(getTableName())
                        .where(BaseColumns._ID + " = ?").whereArgs(id + "")
                        .build())
                        .prepare()
                        .executeAsBlocking());
    }


    public Single<DeleteResult> deleteItem(M model) {
        return Single.fromCallable(() -> storIOSQLite.delete().object(model).prepare().executeAsBlocking());
    }

}
