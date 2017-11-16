package com.kirill.kochnev.homewardrope.di.modules;


import android.support.annotation.NonNull;

import com.kirill.kochnev.homewardrope.enums.ViewMode;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class WardropeModule {

    @NonNull
    private ViewMode mode;

    public WardropeModule(@NonNull final ViewMode mode) {
        this.mode = mode;
    }

    @Provides
    @Named("mode")
    @NonNull
    public ViewMode getMode() {
        return mode;
    }
}
