package com.kirill.kochnev.homewardrope.di;

import com.kirill.kochnev.homewardrope.mvp.presenters.interfaces.ThingsPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Kirill Kochnev on 25.02.17.
 */

@Singleton
@Component(modules = {GreenDaoModule.class})
public interface AppComponent {

     void inject(ThingsPresenter presenter);
}
