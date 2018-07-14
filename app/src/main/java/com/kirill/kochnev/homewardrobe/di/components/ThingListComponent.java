package com.kirill.kochnev.homewardrobe.di.components;


import com.kirill.kochnev.homewardrobe.di.modules.ThingsModule;
import com.kirill.kochnev.homewardrobe.di.scopes.ThingListScope;
import com.kirill.kochnev.homewardrobe.presentation.presenters.thing.ThingsPresenter;

import dagger.Subcomponent;

@ThingListScope
@Subcomponent(modules = ThingsModule.class)
public interface ThingListComponent {

    ThingsPresenter providePresenter();

}
