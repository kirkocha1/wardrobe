package com.kirill.kochnev.homewardrobe.di.components;

import com.kirill.kochnev.homewardrobe.di.modules.AddUpdateThingModule;
import com.kirill.kochnev.homewardrobe.di.modules.AddUpdateWardrobeModule;
import com.kirill.kochnev.homewardrobe.di.modules.CollageModule;
import com.kirill.kochnev.homewardrobe.di.modules.DbModule;
import com.kirill.kochnev.homewardrobe.di.modules.ThingsModule;
import com.kirill.kochnev.homewardrobe.di.modules.UtilsModule;
import com.kirill.kochnev.homewardrobe.di.modules.WardropeModule;
import com.kirill.kochnev.homewardrobe.di.modules.look.LooksModule;
import com.kirill.kochnev.homewardrobe.presentation.presenters.thing.PutThingPresenter;
import com.kirill.kochnev.homewardrobe.presentation.presenters.thing.ThingsPresenter;
import com.kirill.kochnev.homewardrobe.presentation.presenters.wardrobe.PutWardrobePresenter;
import com.kirill.kochnev.homewardrobe.presentation.presenters.wardrobe.WardrobesPresenter;
import com.kirill.kochnev.homewardrobe.presentation.ui.views.ListItemView;
import com.kirill.kochnev.homewardrobe.presentation.ui.views.WardrobeItemView;
import com.kirill.kochnev.homewardrobe.utils.ImageProcessor;
import com.kirill.kochnev.homewardrobe.utils.bus.IdBus;
import com.pushtorefresh.storio.sqlite.StorIOSQLite;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Kirill Kochnev on 25.02.17.
 */

@Singleton
@Component(modules = {DbModule.class, UtilsModule.class})
public interface AppComponent {

    StorIOSQLite provideStorioSql();

    IdBus provideIdBus();

    LookComponent plusComponent(LooksModule module);

    ImageProcessor provideImageHelper();

    WardrobeListComponent plusWardropeComponent(WardropeModule module);

    ThingListComponent plusThingListComponent(ThingsModule module);

    PutWardrobeComponent plusAddUpdateWardropeComponent(AddUpdateWardrobeModule module);

    PutThingComponent plusAddUpdateThingComponent(AddUpdateThingModule module);

    CollageComponent plusCollageComponent(CollageModule module);

    void inject(PutWardrobePresenter presenter);

    void inject(WardrobesPresenter presenter);

    void inject(ThingsPresenter presenter);

    void inject(PutThingPresenter presenter);

    void inject(WardrobeItemView view);

    void inject(ListItemView view);
}
