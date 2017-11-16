package com.kirill.kochnev.homewardrope.di.components;


import com.kirill.kochnev.homewardrope.di.modules.ThingsModule;
import com.kirill.kochnev.homewardrope.di.scopes.ThingListScope;
import com.kirill.kochnev.homewardrope.mvp.presenters.thing.ThingsPresenter;

import dagger.Subcomponent;

@ThingListScope
@Subcomponent(modules = ThingsModule.class)
public interface ThingListComponent {

    ThingsPresenter providePresenter();

}
