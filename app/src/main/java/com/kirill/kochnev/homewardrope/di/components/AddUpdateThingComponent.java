package com.kirill.kochnev.homewardrope.di.components;

import com.kirill.kochnev.homewardrope.di.modules.AddUpdateThingModule;
import com.kirill.kochnev.homewardrope.di.scopes.ThingListScope;
import com.kirill.kochnev.homewardrope.mvp.presenters.thing.AddUpdateThingPresenter;

import dagger.Subcomponent;

@ThingListScope
@Subcomponent(modules = AddUpdateThingModule.class)
public interface AddUpdateThingComponent {

    AddUpdateThingPresenter providePresenter();

}
