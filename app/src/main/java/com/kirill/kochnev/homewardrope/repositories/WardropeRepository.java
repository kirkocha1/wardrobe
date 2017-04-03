package com.kirill.kochnev.homewardrope.repositories;

import com.kirill.kochnev.homewardrope.db.models.DaoSession;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.db.models.ThingDao;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.db.models.WardropeDao;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractWardropeRepository;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.HashSet;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by kirill on 30.03.17.
 */

public class WardropeRepository extends AbstractWardropeRepository {

    private DaoSession session;

    public WardropeRepository(AbstractDao<Wardrope, Long> dao, DaoSession session) {
        super(dao);
        this.session = session;
    }


    @Override
    public Single<Object> putWardropeWithThings(String name, HashSet<Long> thingIds) {
        return Single.create(sub -> {
            try {
                Wardrope wardrope = new Wardrope();
                wardrope.setName(name);
                long wardropeId = dao.insertOrReplace(wardrope);
                List<Thing> things = session.getThingDao().queryBuilder().where(ThingDao.Properties.Id.in(thingIds)).list();
                for (Thing thing : things) {
                    wardrope.getThings().add(thing);
                }
                wardrope.update();
                Wardrope w = dao.load(wardropeId);
                sub.onSuccess(new Object());
            } catch (Exception ex) {
                sub.onError(ex);
            }
        });
    }

    @Override
    public WhereCondition getWhere(long id) {
        return WardropeDao.Properties.Id.gt(id);
    }
}
