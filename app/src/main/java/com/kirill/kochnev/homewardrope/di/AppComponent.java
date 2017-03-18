package com.kirill.kochnev.homewardrope.di;

import com.kirill.kochnev.homewardrope.mvp.presenters.AddUpdateThingPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.ThingsPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Kirill Kochnev on 25.02.17.
 */

@Singleton
@Component(modules = {GreenDaoModule.class, RepositoryModule.class, InteractorModule.class})
public interface AppComponent {

     void inject(ThingsPresenter presenter);
     void inject(AddUpdateThingPresenter presenter);

}
