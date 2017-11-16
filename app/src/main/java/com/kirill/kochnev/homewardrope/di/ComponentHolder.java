package com.kirill.kochnev.homewardrope.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.kirill.kochnev.homewardrope.di.components.AppComponent;
import com.kirill.kochnev.homewardrope.di.components.DaggerAppComponent;
import com.kirill.kochnev.homewardrope.di.components.LookComponent;
import com.kirill.kochnev.homewardrope.di.components.ThingListComponent;
import com.kirill.kochnev.homewardrope.di.components.WardrobeListComponent;
import com.kirill.kochnev.homewardrope.di.modules.DbModule;
import com.kirill.kochnev.homewardrope.di.modules.LooksModule;
import com.kirill.kochnev.homewardrope.di.modules.ThingsModule;
import com.kirill.kochnev.homewardrope.di.modules.WardropeModule;
import com.kirill.kochnev.homewardrope.enums.ViewMode;

public class ComponentHolder {

    private AppComponent mainComponent;
    private LookComponent lookComponent;
    private WardrobeListComponent wardrobeListComponent;
    private ThingListComponent thingListComponent;

    public ComponentHolder(Context context) {
        mainComponent = DaggerAppComponent.builder().dbModule(new DbModule(context)).build();
    }

    @NonNull
    public AppComponent getMainComponent() {
        return mainComponent;
    }

    @NonNull
    public LookComponent getLookComponent() {
        return lookComponent;
    }

    public LookComponent provideLookComponent(long filterId, boolean isEdit, ViewMode mode) {
        if (lookComponent == null) {
            lookComponent = mainComponent.plusComponent(new LooksModule(filterId, isEdit, mode));
        }
        return lookComponent;
    }


    public void clearLookComponent() {
        lookComponent = null;
    }


    @NonNull
    public WardrobeListComponent getWardrobeComponent(@NonNull final ViewMode mode) {
        if (wardrobeListComponent == null) {
            wardrobeListComponent = mainComponent.plusWardropeComponent(new WardropeModule(mode));
        }
        return wardrobeListComponent;
    }

    public void clearWardrobeComponent() {
        wardrobeListComponent = null;
    }


    @NonNull
    public ThingListComponent getThingListComponent(final long filterId, final boolean isEdit, final ViewMode viewMode) {
        if (thingListComponent == null) {
            thingListComponent = mainComponent.plusThingListComponent(new ThingsModule(filterId, isEdit, viewMode));
        }
        return thingListComponent;
    }

    public void clearThingListComponent() {
        thingListComponent = null;
    }
}
