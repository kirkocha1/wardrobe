package com.kirill.kochnev.homewardrope.di.components;

import com.kirill.kochnev.homewardrope.di.modules.look.CreateLookModule;
import com.kirill.kochnev.homewardrope.di.modules.look.LooksModule;
import com.kirill.kochnev.homewardrope.di.modules.look.UpdateLookModule;
import com.kirill.kochnev.homewardrope.di.scopes.LookScope;
import com.kirill.kochnev.homewardrope.presentation.presenters.look.LooksPresenter;

import dagger.Subcomponent;

/**
 * Created by kirill on 12.05.17.
 */

@LookScope
@Subcomponent(modules = {LooksModule.class})
public interface LookComponent {

    LooksPresenter provideLooksPresenter();

    UpdateLookComponent plusUpdateLookComponent(UpdateLookModule module);

    CreateLookComponent plusCreateLookComponent(CreateLookModule module);
}
