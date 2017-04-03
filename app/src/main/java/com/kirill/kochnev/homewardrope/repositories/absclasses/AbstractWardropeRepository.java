package com.kirill.kochnev.homewardrope.repositories.absclasses;

import com.kirill.kochnev.homewardrope.db.models.Wardrope;

import org.greenrobot.greendao.AbstractDao;

import java.util.HashSet;

import io.reactivex.Single;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.schedulers.ScheduledRunnable;

/**
 * Created by kirill on 30.03.17.
 */

public abstract class AbstractWardropeRepository extends AbstractRepository<Wardrope> {

    public AbstractWardropeRepository(AbstractDao<Wardrope, Long> dao) {
        super(dao);
    }

    public abstract Single<Object> putWardropeWithThings(String name, HashSet<Long> thingIds);

}
