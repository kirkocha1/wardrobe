package com.kirill.kochnev.homewardrope.repositories.absclasses;

import com.kirill.kochnev.homewardrope.db.models.Wardrope;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by kirill on 30.03.17.
 */

public abstract class AbstractWardropeRepository extends AbstractRepository<Wardrope> {

    public AbstractWardropeRepository(AbstractDao<Wardrope, Long> dao) {
        super(dao);
    }
}
