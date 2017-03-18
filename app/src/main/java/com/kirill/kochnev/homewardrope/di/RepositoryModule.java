package com.kirill.kochnev.homewardrope.di;

import com.kirill.kochnev.homewardrope.db.models.ThingDao;
import com.kirill.kochnev.homewardrope.repositories.ThingsRepository;
import com.kirill.kochnev.homewardrope.repositories.interfaces.IThingsRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kirill Kochnev on 19.03.17.
 */

@Module
public class RepositoryModule {

    @Provides
    IThingsRepository provideThingsRepository(ThingDao dao) {
        return new ThingsRepository(dao);
    }

}
