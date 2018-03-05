package com.kirill.kochnev.homewardrope.di.modules;

import android.support.annotation.NonNull;

import com.kirill.kochnev.homewardrope.di.scopes.CollageScope;

import java.util.HashSet;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class CollageModule {

    private @NonNull final HashSet<Long> thingIds;

    public CollageModule(@NonNull final HashSet<Long> thingIds) {
        this.thingIds = thingIds;
    }

    @Provides
    @CollageScope
    @Named("ThingIds")
    public HashSet<Long> provideThingIds() {
        return thingIds;
    }
}
