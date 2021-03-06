package com.kirill.kochnev.homewardrobe.di.modules;

import android.support.annotation.NonNull;

import com.kirill.kochnev.homewardrobe.di.scopes.ThingListScope;
import com.kirill.kochnev.homewardrobe.enums.ViewMode;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ThingsModule {

    private long filterId;

    private boolean isEdit;

    @NonNull
    private ViewMode viewMode;

    public ThingsModule(final long filterId, final boolean isEdit, @NonNull final ViewMode viewMode) {
        this.filterId = filterId;
        this.isEdit = isEdit;
        this.viewMode = viewMode;
    }

    @ThingListScope
    @Provides
    @Named("filterId")
    public long provideFilterId() {
        return filterId;
    }

    @ThingListScope
    @Provides
    @Named("isEdit")
    public boolean provideIsEdit() {
        return isEdit;
    }

    @ThingListScope
    @Provides
    @Named("mode")
    public ViewMode provideViewMode() {
        return viewMode;
    }
}
