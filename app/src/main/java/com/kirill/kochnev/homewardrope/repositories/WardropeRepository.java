package com.kirill.kochnev.homewardrope.repositories;

import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.db.models.WardropeDao;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractWardropeRepository;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.WhereCondition;

/**
 * Created by kirill on 30.03.17.
 */

public class WardropeRepository extends AbstractWardropeRepository {

    public WardropeRepository(AbstractDao<Wardrope, Long> dao) {
        super(dao);
    }

    @Override
    public WhereCondition getWhere(long id) {
        return WardropeDao.Properties.Id.gt(id);
    }
}
