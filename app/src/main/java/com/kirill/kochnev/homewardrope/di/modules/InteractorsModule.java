package com.kirill.kochnev.homewardrope.di.modules;

import com.kirill.kochnev.homewardrope.interactors.AddUpdateThingsInteractor;
import com.kirill.kochnev.homewardrope.interactors.AddUpdateWardropeInteractor;
import com.kirill.kochnev.homewardrope.interactors.ThingsInteractor;
import com.kirill.kochnev.homewardrope.interactors.WardropesInteractor;
import com.kirill.kochnev.homewardrope.repositories.ThingRepository;
import com.kirill.kochnev.homewardrope.repositories.WardropeRepository;
import com.kirill.kochnev.homewardrope.utils.ImageHelper;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kirill on 11.05.17.
 */

@Module
public class InteractorsModule {

    @Provides
    ThingsInteractor provideThingInteractor(ImageHelper helper, ThingRepository repository) {
        return new ThingsInteractor(repository, helper);
    }

    @Provides
    AddUpdateThingsInteractor provideAddUpdateThingInteractor(ImageHelper helper, ThingRepository repository) {
        return new AddUpdateThingsInteractor(helper, repository);
    }

    @Provides
    AddUpdateWardropeInteractor provideAddUpdateWardropesInteractor(WardropeRepository repository) {
        return new AddUpdateWardropeInteractor(repository);
    }

    @Provides
    WardropesInteractor provideWardropesInteractor(WardropeRepository repository) {
        return new WardropesInteractor(repository);
    }

}
