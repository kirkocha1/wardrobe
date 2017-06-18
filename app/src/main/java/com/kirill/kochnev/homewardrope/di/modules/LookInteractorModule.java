package com.kirill.kochnev.homewardrope.di.modules;

import com.kirill.kochnev.homewardrope.di.scopes.LookScope;
import com.kirill.kochnev.homewardrope.interactors.CollageInteractor;
import com.kirill.kochnev.homewardrope.interactors.LooksInteractor;
import com.kirill.kochnev.homewardrope.interactors.interfaces.ICollageInteractor;
import com.kirill.kochnev.homewardrope.interactors.interfaces.ILooksInteractor;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractLookRepository;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractThingRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kirill on 12.05.17.
 */


@Module
public class LookInteractorModule {

    @LookScope
    @Provides
    ILooksInteractor provideLookssInteractor(AbstractLookRepository repository) {
        return new LooksInteractor(repository);
    }

    @Provides
    ICollageInteractor provideCollageInteractor(AbstractThingRepository repository) {
        return new CollageInteractor(repository);
    }
}
