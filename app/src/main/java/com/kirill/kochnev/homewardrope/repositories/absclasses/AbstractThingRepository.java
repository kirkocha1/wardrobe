package com.kirill.kochnev.homewardrope.repositories.absclasses;

import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractRepository;

import org.greenrobot.greendao.AbstractDao;

import io.reactivex.Single;

/**
 * Created by kirill on 30.03.17.
 */

public abstract class AbstractThingRepository extends AbstractRepository<Thing> {

    public AbstractThingRepository(AbstractDao<Thing, Long> dao) {
        super(dao);
    }

    @Override
    public Single<Boolean> deletItem(Thing model) {
        return Single.create(sub -> {
            try {
                dao.delete(model);
                sub.onSuccess(true);
            } catch (Exception ex) {
                sub.onError(ex);
            }
        });
    }
}
