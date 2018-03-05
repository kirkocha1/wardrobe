package com.kirill.kochnev.homewardrope.di.components;

import com.kirill.kochnev.homewardrope.di.modules.CollageModule;
import com.kirill.kochnev.homewardrope.di.scopes.CollageScope;
import com.kirill.kochnev.homewardrope.mvp.presenters.look.CollagePresenter;

import dagger.Subcomponent;

@CollageScope
@Subcomponent(modules = {CollageModule.class})
public interface CollageComponent {

    CollagePresenter providePresenter();
}
