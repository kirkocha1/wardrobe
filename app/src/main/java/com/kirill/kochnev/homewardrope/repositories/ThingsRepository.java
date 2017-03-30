package com.kirill.kochnev.homewardrope.repositories;

import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.db.models.ThingDao;
import com.kirill.kochnev.homewardrope.repositories.interfaces.IRepository;
import com.kirill.kochnev.homewardrope.repositories.interfaces.IThingsRepository;

import java.util.List;

/**
 * Created by Kirill Kochnev on 19.03.17.
 */

public class ThingsRepository implements IThingsRepository {

    private ThingDao dao;

    public ThingsRepository(ThingDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Thing> getNextList(long id) {
        return null;
    }

    @Override
    public void putItem(Thing model) {

    }

    @Override
    public Thing getItem(long id) {
        return null;
    }
}
