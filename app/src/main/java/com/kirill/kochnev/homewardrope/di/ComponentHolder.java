package com.kirill.kochnev.homewardrope.di;

import android.content.Context;
import android.support.annotation.NonNull;

import com.kirill.kochnev.homewardrope.di.components.AppComponent;
import com.kirill.kochnev.homewardrope.di.components.CollageComponent;
import com.kirill.kochnev.homewardrope.di.components.CreateLookComponent;
import com.kirill.kochnev.homewardrope.di.components.DaggerAppComponent;
import com.kirill.kochnev.homewardrope.di.components.LookComponent;
import com.kirill.kochnev.homewardrope.di.components.PutThingComponent;
import com.kirill.kochnev.homewardrope.di.components.PutWardrobeComponent;
import com.kirill.kochnev.homewardrope.di.components.ThingListComponent;
import com.kirill.kochnev.homewardrope.di.components.UpdateLookComponent;
import com.kirill.kochnev.homewardrope.di.components.WardrobeListComponent;
import com.kirill.kochnev.homewardrope.di.modules.AddUpdateThingModule;
import com.kirill.kochnev.homewardrope.di.modules.AddUpdateWardrobeModule;
import com.kirill.kochnev.homewardrope.di.modules.CollageModule;
import com.kirill.kochnev.homewardrope.di.modules.DbModule;
import com.kirill.kochnev.homewardrope.di.modules.ThingsModule;
import com.kirill.kochnev.homewardrope.di.modules.WardropeModule;
import com.kirill.kochnev.homewardrope.di.modules.look.CreateLookModule;
import com.kirill.kochnev.homewardrope.di.modules.look.LooksModule;
import com.kirill.kochnev.homewardrope.di.modules.look.UpdateLookModule;
import com.kirill.kochnev.homewardrope.enums.ViewMode;

import java.util.HashSet;

public class ComponentHolder {

    private AppComponent mainComponent;
    private LookComponent lookComponent;
    private UpdateLookComponent updateLookComponent;
    private CreateLookComponent createLookComponent;
    private CollageComponent collageComponent;
    private WardrobeListComponent wardrobeListComponent;
    private ThingListComponent thingListComponent;
    private PutThingComponent putThingComponent;
    private PutWardrobeComponent putWardrobeComponent;

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

    @NonNull
    public PutWardrobeComponent getAddUpdateWardrobeComponent(final long wardrobeId) {
        if (putWardrobeComponent == null) {
            putWardrobeComponent = mainComponent.plusAddUpdateWardropeComponent(new AddUpdateWardrobeModule(wardrobeId));
        }
        return putWardrobeComponent;
    }


    @NonNull
    public PutThingComponent getAddUpdateThingComponent(final long thingId) {
        if (putThingComponent == null) {
            putThingComponent = mainComponent.plusAddUpdateThingComponent(new AddUpdateThingModule(thingId));
        }
        return putThingComponent;
    }

    @NonNull
    public UpdateLookComponent getUpdateLookComponent(final long lookId) {
        if (updateLookComponent == null) {
            updateLookComponent = lookComponent.plusUpdateLookComponent(new UpdateLookModule(lookId));
        }
        return updateLookComponent;
    }

    @NonNull
    public CreateLookComponent getCreateLookComponent(final long lookId) {
        if (createLookComponent == null) {
            createLookComponent = lookComponent.plusCreateLookComponent(new CreateLookModule(lookId));
        }
        return createLookComponent;
    }

    @NonNull
    public CollageComponent getCreateCollageComponent(@NonNull final HashSet<Long> thingIds) {
        if (collageComponent == null) {
            collageComponent = mainComponent.plusCollageComponent(new CollageModule(thingIds));
        }
        return collageComponent;
    }

    public void clearCollageComponent() {
        collageComponent = null;
    }

    public void clearAddUpdateThingComponent() {
        putThingComponent = null;
    }

    public void clearAddUpdateWardrobeComponent() {
        putWardrobeComponent = null;
    }

    public void clearThingListComponent() {
        thingListComponent = null;
    }

    public void clearLookComponent() {
        lookComponent = null;
    }

    public void clearCreateLookComponent() {
        createLookComponent = null;
    }

    public void clearUpdateLookComponent() {
        updateLookComponent = null;
    }

}
