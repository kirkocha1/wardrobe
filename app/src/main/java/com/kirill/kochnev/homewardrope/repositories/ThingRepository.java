package com.kirill.kochnev.homewardrope.repositories;

import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.db.models.ThingDao;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractThingRepository;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.WhereCondition;

/**
 * Created by kirill on 30.03.17.
 */

public class ThingRepository extends AbstractThingRepository {

    public ThingRepository(AbstractDao<Thing, Long> dao) {
        super(dao);
    }

    @Override
    public WhereCondition getWhere(long id) {
        return ThingDao.Properties.Id.gt(id);
    }
}
