package com.kirill.kochnev.homewardrope.di.modules;

import com.kirill.kochnev.homewardrope.repositories.LookRepository;
import com.kirill.kochnev.homewardrope.repositories.ThingRepository;
import com.kirill.kochnev.homewardrope.repositories.WardropeRepository;
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
    ThingRepository provideThingsRepository(StorIOSQLite storIOSQLite) {
        return new ThingRepository(storIOSQLite);
    }

    @Singleton
    @Provides
    WardropeRepository provideWardropeRepository(StorIOSQLite storIOSQLite) {
        return new WardropeRepository(storIOSQLite);
    }

    @Singleton
    @Provides
    LookRepository provideLookRepository(StorIOSQLite storIOSQLite) {
        return new LookRepository(storIOSQLite);
    }
}
