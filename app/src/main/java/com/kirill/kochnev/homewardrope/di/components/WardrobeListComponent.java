package com.kirill.kochnev.homewardrope.di.components;


import com.kirill.kochnev.homewardrope.di.modules.WardropeModule;
import com.kirill.kochnev.homewardrope.di.scopes.WardropeScope;
import com.kirill.kochnev.homewardrope.mvp.presenters.wardrope.WardropesPresenter;

import dagger.Subcomponent;

@WardropeScope
@Subcomponent(modules = {WardropeModule.class})
public interface WardrobeListComponent {

    WardropesPresenter providePresenter();

}
