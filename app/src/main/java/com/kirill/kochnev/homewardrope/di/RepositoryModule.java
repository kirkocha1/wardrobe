package com.kirill.kochnev.homewardrope.di;

import com.kirill.kochnev.homewardrope.db.models.ThingDao;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractThingRepository;
import com.kirill.kochnev.homewardrope.repositories.ThingRepository;
import com.kirill.kochnev.homewardrope.repositories.interfaces.IThingsRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kirill Kochnev on 19.03.17.
 */

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    AbstractThingRepository provideTRepository(ThingDao dao) {
        return new ThingRepository(dao);
    }

}
