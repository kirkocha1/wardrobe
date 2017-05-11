package com.kirill.kochnev.homewardrope.di;

import com.kirill.kochnev.homewardrope.mvp.presenters.AddUpdateThingPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.AddUpdateWardropePresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.CollagePresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.CreationLookPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.LooksPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.ThingsPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.UpdateLookPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.WardropesPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Kirill Kochnev on 25.02.17.
 */

@Singleton
@Component(modules = {RepositoryModule.class, DbModule.class, InteractorModule.class})
public interface AppComponent {

    void inject(AddUpdateWardropePresenter presenter);

    void inject(WardropesPresenter presenter);

    void inject(ThingsPresenter presenter);

    void inject(AddUpdateThingPresenter presenter);

    void inject(LooksPresenter presenter);

    void inject(CreationLookPresenter activity);

    void inject(CollagePresenter presenter);

    void inject(UpdateLookPresenter presenter);

}
