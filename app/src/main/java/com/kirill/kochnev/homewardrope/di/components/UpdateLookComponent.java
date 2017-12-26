package com.kirill.kochnev.homewardrope.di.components;


import com.kirill.kochnev.homewardrope.di.modules.look.UpdateLookModule;
import com.kirill.kochnev.homewardrope.mvp.presenters.look.UpdateLookPresenter;

import dagger.Subcomponent;

@Subcomponent(modules = UpdateLookModule.class)
public interface UpdateLookComponent {

    UpdateLookPresenter provideUpdateLookPresenter();
}
