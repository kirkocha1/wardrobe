package com.kirill.kochnev.homewardrobe.di.components;

import com.kirill.kochnev.homewardrobe.di.modules.AddUpdateThingModule;
import com.kirill.kochnev.homewardrobe.di.scopes.ThingListScope;
import com.kirill.kochnev.homewardrobe.presentation.presenters.thing.PutThingPresenter;

import dagger.Subcomponent;

@ThingListScope
@Subcomponent(modules = AddUpdateThingModule.class)
public interface PutThingComponent {

    PutThingPresenter providePresenter();

}
