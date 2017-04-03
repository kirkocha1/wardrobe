package com.kirill.kochnev.homewardrope.di;

import com.kirill.kochnev.homewardrope.repositories.ThingRepository;
import com.kirill.kochnev.homewardrope.repositories.WardropeRepository;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractThingRepository;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractWardropeRepository;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

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
    AbstractThingRepository provideThingsRepository(StorIOSQLite storIOSQLite) {
        return new ThingRepository(storIOSQLite);
    }

    @Singleton
    @Provides
    AbstractWardropeRepository provideWardropeRepository(StorIOSQLite storIOSQLite) {
        return new WardropeRepository(storIOSQLite);
    }
}
