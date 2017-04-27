package com.kirill.kochnev.homewardrope.di;

import com.kirill.kochnev.homewardrope.mvp.presenters.AddUpdateThingPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.AddUpdateWardropePresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.CollagePresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.FirstStepCreationLookPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.LooksPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.ThingsPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.WardropesPresenter;
import com.kirill.kochnev.homewardrope.ui.activities.look.AddUpdateLookActivity;

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

    void inject(LooksPresenter presenter);

    void inject(AddUpdateLookActivity activity);

    void inject(FirstStepCreationLookPresenter activity);

    void inject(CollagePresenter presenter);


}
