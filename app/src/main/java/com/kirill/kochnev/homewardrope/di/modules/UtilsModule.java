package com.kirill.kochnev.homewardrope.di.modules;

import com.kirill.kochnev.homewardrope.utils.bus.IdBus;
import com.kirill.kochnev.homewardrope.utils.bus.RxBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kirill on 28.04.17.
 */

@Module
@Singleton
public class UtilsModule {

    @Singleton
    @Provides
    public IdBus provideRxBus() {
        return new RxBus();
    }
}
