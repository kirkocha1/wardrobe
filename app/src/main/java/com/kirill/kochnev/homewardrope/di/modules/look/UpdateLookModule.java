package com.kirill.kochnev.homewardrope.di.modules.look;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class UpdateLookModule {

    private long lookId;

    public UpdateLookModule(long lookId) {
        this.lookId = lookId;
    }

    @Provides
    @Named("lookId")
    public long provideLookId() {
        return lookId;
    }

}
