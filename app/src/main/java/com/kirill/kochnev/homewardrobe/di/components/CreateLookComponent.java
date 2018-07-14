package com.kirill.kochnev.homewardrobe.di.components;


import com.kirill.kochnev.homewardrobe.di.modules.look.CreateLookModule;
import com.kirill.kochnev.homewardrobe.presentation.presenters.look.CreationLookPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = CreateLookModule.class)
public interface CreateLookComponent {

    CreationLookPresenter providePresenter();

}
