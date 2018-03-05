package com.kirill.kochnev.homewardrope.di.components;

import com.kirill.kochnev.homewardrope.di.modules.AddUpdateThingModule;
import com.kirill.kochnev.homewardrope.di.modules.AddUpdateWardrobeModule;
import com.kirill.kochnev.homewardrope.di.modules.CollageModule;
import com.kirill.kochnev.homewardrope.di.modules.DbModule;
import com.kirill.kochnev.homewardrope.di.modules.ThingsModule;
import com.kirill.kochnev.homewardrope.di.modules.UtilsModule;
import com.kirill.kochnev.homewardrope.di.modules.WardropeModule;
import com.kirill.kochnev.homewardrope.di.modules.look.LooksModule;
import com.kirill.kochnev.homewardrope.mvp.presenters.thing.AddUpdateThingPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.thing.ThingsPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.wardrobe.AddUpdateWardrobePresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.wardrobe.WardrobesPresenter;
import com.kirill.kochnev.homewardrope.ui.views.ListItemView;
import com.kirill.kochnev.homewardrope.ui.views.WardrobeItemView;
import com.kirill.kochnev.homewardrope.utils.ImageHelper;
import com.kirill.kochnev.homewardrope.utils.bus.IdBus;
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

    ImageHelper provideImageHelper();

    WardrobeListComponent plusWardropeComponent(WardropeModule module);

    ThingListComponent plusThingListComponent(ThingsModule module);

    AddUpdateWardropeComponent plusAddUpdateWardropeComponent(AddUpdateWardrobeModule module);

    AddUpdateThingComponent plusAddUpdateThingComponent(AddUpdateThingModule module);

    CollageComponent plusCollageComponent(CollageModule module);

    void inject(AddUpdateWardrobePresenter presenter);

    void inject(WardrobesPresenter presenter);

    void inject(ThingsPresenter presenter);

    void inject(AddUpdateThingPresenter presenter);

    void inject(WardrobeItemView view);

    void inject(ListItemView view);
}
