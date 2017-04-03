package com.kirill.kochnev.homewardrope.repositories;

import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.db.models.WardropeDao;
import com.kirill.kochnev.homewardrope.db.models.manytomany.ThingsWardropes;
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

    public WardropeRepository(AbstractDao<Wardrope, Long> dao) {
        super(dao);
    }


    @Override
    public Single<Object> putWardropeWithThings(String name, HashSet<Long> thingIds) {
        return Single.create(sub -> {
            try {
                Wardrope wardrope = new Wardrope();
                wardrope.setName(name);
                long wardropeId = dao.insertOrReplace(wardrope);
                for (Long id : thingIds) {
                    ThingsWardropes entity = new ThingsWardropes();
                    entity.setThingId(id);
                    entity.setWardropeId(wardropeId);
                    wardrope.getThingsWardropes().add(entity);
                }
                long id = dao.insertOrReplace(wardrope);

                List<Wardrope> ls = dao.queryBuilder().where(WardropeDao.Properties.Id.eq(wardrope.getId())).list();
                for (Wardrope w : ls) {
                    List<ThingsWardropes> f = w.getThingsWardropes();
                }
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
