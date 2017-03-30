package com.kirill.kochnev.homewardrope.di;

import com.kirill.kochnev.homewardrope.interactors.AddThingInteractor;
import com.kirill.kochnev.homewardrope.repositories.interfaces.IThingsRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kirill Kochnev on 19.03.17.
 */

@Module
public class InteractorModule {

    @Provides
    AddThingInteractor provideAddThingInteractor(IThingsRepository repository) {
        return new AddThingInteractor(repository);
    }
}
