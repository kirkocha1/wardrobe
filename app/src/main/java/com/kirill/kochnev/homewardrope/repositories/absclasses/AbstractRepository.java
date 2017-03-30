package com.kirill.kochnev.homewardrope.repositories.absclasses;

import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.repositories.interfaces.IRepository;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by kirill on 30.03.17.
 */

//Abstract repository for common CRUD operation
public abstract class AbstractRepository<M extends IDbModel> implements IRepository<M> {

    public AbstractDao<M, Long> dao;

    private QueryBuilder<M> getNextBatch;

    private QueryBuilder<M> getFirstBatch;

    public AbstractRepository(AbstractDao<M, Long> dao) {
        this.dao = dao;
    }

    @Override
    public Single<List<M>> getNextList(long id) {
        return Single.create(sub -> {
            List<M> models = null;
            if (id != -1) {
                if (getNextBatch == null) {
                    getNextBatch = dao.queryBuilder().where(getWhere(id)).limit(LIMIT);
                }
                models = getNextBatch.list();
            } else {
                if (getFirstBatch == null) {
                    getFirstBatch = dao.queryBuilder().limit(LIMIT);
                }
                models = getFirstBatch.list();
            }
            if (models != null) {
                sub.onSuccess(models);
            } else {
                sub.onError(new Exception("nothing to load"));
            }
        });
    }

    @Override
    public Single<Boolean> putItem(M model) {
        return Single.create(sub -> {
            try {
                dao.insertOrReplace(model);
                sub.onSuccess(true);
            } catch (Exception ex) {
                sub.onError(ex);
            }
        });
    }

    @Override
    public Single<M> getItem(long id) {
        return Single.create(sub -> {
            M thing = dao.load(id);
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
                dao.delete(model);
                sub.onSuccess(true);
            } catch (Exception ex) {
                sub.onError(ex);
            }
        });
    }

    public abstract WhereCondition getWhere(long id);

}
