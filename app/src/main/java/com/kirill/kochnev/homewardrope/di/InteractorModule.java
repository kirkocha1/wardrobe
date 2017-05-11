package com.kirill.kochnev.homewardrope.di;

import com.kirill.kochnev.homewardrope.interactors.AddUpdateThingsInteractor;
import com.kirill.kochnev.homewardrope.interactors.AddUpdateWardropeInteractor;
import com.kirill.kochnev.homewardrope.interactors.ThingsInteractor;
import com.kirill.kochnev.homewardrope.interactors.WardropesInteractor;
import com.kirill.kochnev.homewardrope.interactors.interfaces.IAddUpdateThingsInteractor;
import com.kirill.kochnev.homewardrope.interactors.interfaces.IAddUpdateWardropeInteractor;
import com.kirill.kochnev.homewardrope.interactors.interfaces.IThingInteractor;
import com.kirill.kochnev.homewardrope.interactors.interfaces.IWardropesInteractor;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractThingRepository;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractWardropeRepository;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kirill on 11.05.17.
 */

@Module
public class InteractorModule {

    @Provides
    IThingInteractor provideThingInteractor(AbstractThingRepository repository) {
        return new ThingsInteractor(repository);
    }

    @Provides
    IAddUpdateThingsInteractor provideAddUpdateThingInteractor(AbstractThingRepository repository) {
        return new AddUpdateThingsInteractor(repository);
    }

    @Provides
    IAddUpdateWardropeInteractor provideAddUpdateWardropesInteractor(AbstractWardropeRepository repository) {
        return new AddUpdateWardropeInteractor(repository);
    }

    @Provides
    IWardropesInteractor provideWardropesInteractor(AbstractWardropeRepository repository) {
        return new WardropesInteractor(repository);
    }


}
