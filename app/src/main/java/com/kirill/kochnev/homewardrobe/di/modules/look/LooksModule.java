package com.kirill.kochnev.homewardrobe.di.modules.look;


import com.kirill.kochnev.homewardrobe.di.scopes.LookScope;
import com.kirill.kochnev.homewardrobe.enums.ViewMode;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class LooksModule {

    private long filterId;
    private boolean isEdit;
    private ViewMode viewMode;

    public LooksModule(final long filterId, final boolean isEdit, final ViewMode viewMode) {
        this.filterId = filterId;
        this.isEdit = isEdit;
        this.viewMode = viewMode;
    }

    @LookScope
    @Provides
    @Named("filterId")
    public long provideFilterId() {
        return filterId;
    }

    @LookScope
    @Provides
    @Named("isEdit")
    public boolean provideIsEdit() {
        return isEdit;
    }

    @LookScope
    @Provides
    @Named("mode")
    public ViewMode provideViewMode() {
        return viewMode;
    }

}
