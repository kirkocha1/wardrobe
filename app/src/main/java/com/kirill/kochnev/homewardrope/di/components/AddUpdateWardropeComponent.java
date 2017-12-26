package com.kirill.kochnev.homewardrope.di.components;

import com.kirill.kochnev.homewardrope.di.modules.AddUpdateWardrobeModule;
import com.kirill.kochnev.homewardrope.di.scopes.WardropeScope;
import com.kirill.kochnev.homewardrope.mvp.presenters.wardrope.AddUpdateWardropePresenter;

import dagger.Subcomponent;

@WardropeScope
@Subcomponent(modules = AddUpdateWardrobeModule.class)
public interface AddUpdateWardropeComponent {

    AddUpdateWardropePresenter providePresenter();
}
