package com.kirill.kochnev.homewardrope.repositories;

import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractWardropeRepository;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import java.util.HashSet;

import io.reactivex.Single;

/**
 * Created by kirill on 30.03.17.
 */

public class WardropeRepository extends AbstractWardropeRepository {

    public WardropeRepository(StorIOSQLite storIOSQLite) {
        super(storIOSQLite);

    }


    @Override
    public Single<Object> putWardropeWithThings(String name, HashSet<Long> thingIds) {
        return Single.create(sub -> {
//            try {
//                Wardrope wardrope = new Wardrope();
//                wardrope.setName(name);
//                long wardropeId = dao.insertOrReplace(wardrope);
//                List<Thing> things = session.getThingDao().queryBuilder().where(ThingDao.Properties.Id.in(thingIds)).list();
//                for (Thing thing : things) {
//                    wardrope.getThings().add(thing);
//                }
//                wardrope.update();
//                Wardrope w = dao.load(wardropeId);
//                sub.onSuccess(new Object());
//            } catch (Exception ex) {
//                sub.onError(ex);
//            }
        });
    }

    @Override
    public Class<Wardrope> getEntityClass() {
        return Wardrope.class;
    }

    @Override
    public String getTableName() {
        return "wardropes";
    }
}
