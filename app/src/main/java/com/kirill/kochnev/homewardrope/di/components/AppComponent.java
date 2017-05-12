package com.kirill.kochnev.homewardrope.di.components;

import com.kirill.kochnev.homewardrope.di.modules.DbModule;
import com.kirill.kochnev.homewardrope.di.modules.InteractorsModule;
import com.kirill.kochnev.homewardrope.di.modules.RepositoryModule;
import com.kirill.kochnev.homewardrope.mvp.presenters.thing.AddUpdateThingPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.wardrope.AddUpdateWardropePresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.look.CollagePresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.look.CreationLookPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.look.LooksPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.thing.ThingsPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.look.UpdateLookPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.wardrope.WardropesPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Kirill Kochnev on 25.02.17.
 */

@Singleton
@Component(modules = {RepositoryModule.class, DbModule.class, InteractorsModule.class})
public interface AppComponent {

    LookComponent plusComponent();

    void inject(AddUpdateWardropePresenter presenter);

    void inject(WardropesPresenter presenter);

    void inject(ThingsPresenter presenter);

    void inject(AddUpdateThingPresenter presenter);

}
