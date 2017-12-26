package com.kirill.kochnev.homewardrope.di.components;

import com.kirill.kochnev.homewardrope.di.modules.DbModule;
import com.kirill.kochnev.homewardrope.di.modules.LooksModule;
import com.kirill.kochnev.homewardrope.di.modules.ThingsModule;
import com.kirill.kochnev.homewardrope.di.modules.UtilsModule;
import com.kirill.kochnev.homewardrope.di.modules.WardropeModule;
import com.kirill.kochnev.homewardrope.mvp.presenters.thing.AddUpdateThingPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.thing.ThingsPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.wardrope.AddUpdateWardropePresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.wardrope.WardropesPresenter;
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

    void inject(AddUpdateWardropePresenter presenter);

    void inject(WardropesPresenter presenter);

    void inject(ThingsPresenter presenter);

    void inject(AddUpdateThingPresenter presenter);

    void inject(WardrobeItemView view);

    void inject(ListItemView view);
}
