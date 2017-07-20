package com.kirill.kochnev.homewardrope.di.modules;

import com.kirill.kochnev.homewardrope.di.scopes.LookScope;
import com.kirill.kochnev.homewardrope.interactors.CollageInteractor;
import com.kirill.kochnev.homewardrope.interactors.LooksInteractor;
import com.kirill.kochnev.homewardrope.repositories.LookRepository;
import com.kirill.kochnev.homewardrope.repositories.ThingRepository;
import com.kirill.kochnev.homewardrope.utils.ImageHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kirill on 12.05.17.
 */


@Module
public class LookInteractorModule {

    @LookScope
    @Provides
    LooksInteractor provideLookssInteractor(LookRepository repository, ImageHelper helper) {
        return new LooksInteractor(repository, helper);
    }

    @Provides
    CollageInteractor provideCollageInteractor(ThingRepository repository, ImageHelper helper) {
        return new CollageInteractor(repository, helper);
    }
}
