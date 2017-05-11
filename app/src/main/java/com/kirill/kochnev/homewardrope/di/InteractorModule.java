package com.kirill.kochnev.homewardrope.di;

import com.kirill.kochnev.homewardrope.interactors.ThingsInteractor;
import com.kirill.kochnev.homewardrope.interactors.interfaces.IThingInteractor;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractThingRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kirill on 11.05.17.
 */

@Module
public class InteractorModule {

    @Provides
    IThingInteractor provideInteractor(AbstractThingRepository repository) {
        return new ThingsInteractor(repository);
    }


}
