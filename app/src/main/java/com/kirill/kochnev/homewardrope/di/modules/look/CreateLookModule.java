package com.kirill.kochnev.homewardrope.di.modules.look;


import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class CreateLookModule {

    private long lookId;

    public CreateLookModule(long lookId) {
        this.lookId = lookId;
    }

    @Provides
    @Named("lookId")
    public long provideLookId() {
        return lookId;
    }
}
