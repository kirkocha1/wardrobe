package com.kirill.kochnev.homewardrobe.di.components;


import com.kirill.kochnev.homewardrobe.di.modules.look.UpdateLookModule;
import com.kirill.kochnev.homewardrobe.presentation.presenters.look.UpdateLookPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = UpdateLookModule.class)
public interface UpdateLookComponent {

    UpdateLookPresenter provideUpdateLookPresenter();
}
