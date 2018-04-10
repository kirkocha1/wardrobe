package com.kirill.kochnev.homewardrope.di.components;


import com.kirill.kochnev.homewardrope.di.modules.look.CreateLookModule;
import com.kirill.kochnev.homewardrope.presentation.presenters.look.CreationLookPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = CreateLookModule.class)
public interface CreateLookComponent {

    CreationLookPresenter providePresenter();

}
