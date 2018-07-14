package com.kirill.kochnev.homewardrobe.di.components;

import com.kirill.kochnev.homewardrobe.di.modules.AddUpdateWardrobeModule;
import com.kirill.kochnev.homewardrobe.di.scopes.WardropeScope;
import com.kirill.kochnev.homewardrobe.presentation.presenters.wardrobe.PutWardrobePresenter;

import dagger.Subcomponent;

@WardropeScope
@Subcomponent(modules = AddUpdateWardrobeModule.class)
public interface PutWardrobeComponent {

    PutWardrobePresenter providePresenter();
}
