package com.kirill.kochnev.homewardrobe.di.components;

import com.kirill.kochnev.homewardrobe.di.modules.CollageModule;
import com.kirill.kochnev.homewardrobe.di.scopes.CollageScope;
import com.kirill.kochnev.homewardrobe.presentation.presenters.look.CollagePresenter;

import dagger.Subcomponent;

@CollageScope
@Subcomponent(modules = {CollageModule.class})
public interface CollageComponent {

    CollagePresenter providePresenter();
}
