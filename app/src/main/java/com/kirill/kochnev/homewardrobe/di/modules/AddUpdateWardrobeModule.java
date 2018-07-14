package com.kirill.kochnev.homewardrobe.di.modules;

import com.kirill.kochnev.homewardrobe.di.scopes.WardropeScope;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class AddUpdateWardrobeModule {

    private long wardrobeId;

    public AddUpdateWardrobeModule(long wardrobeId) {
        this.wardrobeId = wardrobeId;
    }


    @WardropeScope
    @Named("wardrobeId")
    @Provides
    public long provideWardrobeId() {
        return wardrobeId;
    }
}
