package com.kirill.kochnev.homewardrope.di.modules;


import com.kirill.kochnev.homewardrope.di.scopes.ThingListScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class AddUpdateThingModule {

    private long thingId;

    public AddUpdateThingModule(final long thingId) {
        this.thingId = thingId;
    }

    @ThingListScope
    @Provides
    @Named("thingId")
    public long provideThingId() {
        return thingId;
    }
}
