package com.kirill.kochnev.homewardrope.di;

import com.kirill.kochnev.homewardrope.mvp.presenters.AddUpdateThingPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.AddUpdateWardropePresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.ThingsPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.WardropesPresenter;
import com.kirill.kochnev.homewardrope.ui.fragments.TestFragment;
import com.kirill.kochnev.homewardrope.ui.views.CollageItemView;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Kirill Kochnev on 25.02.17.
 */

@Singleton
@Component(modules = {RepositoryModule.class, DbModule.class})
public interface AppComponent {

    void inject(AddUpdateWardropePresenter presenter);

    void inject(WardropesPresenter presenter);

    void inject(ThingsPresenter presenter);

    void inject(AddUpdateThingPresenter presenter);

    void inject(TestFragment fragment);
}
