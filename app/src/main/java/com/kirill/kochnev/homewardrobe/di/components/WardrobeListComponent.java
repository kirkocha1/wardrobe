package com.kirill.kochnev.homewardrobe.di.components;


import com.kirill.kochnev.homewardrobe.di.modules.WardropeModule;
import com.kirill.kochnev.homewardrobe.di.scopes.WardropeScope;
import com.kirill.kochnev.homewardrobe.presentation.presenters.wardrobe.WardrobesPresenter;

import dagger.Subcomponent;

@WardropeScope
@Subcomponent(modules = {WardropeModule.class})
public interface WardrobeListComponent {

    WardrobesPresenter providePresenter();

}
