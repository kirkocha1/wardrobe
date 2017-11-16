package com.kirill.kochnev.homewardrope.di.components;

import com.kirill.kochnev.homewardrope.di.modules.LooksModule;
import com.kirill.kochnev.homewardrope.di.scopes.LookScope;
import com.kirill.kochnev.homewardrope.mvp.presenters.look.CollagePresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.look.CreationLookPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.look.LooksPresenter;
import com.kirill.kochnev.homewardrope.mvp.presenters.look.UpdateLookPresenter;

import dagger.Subcomponent;

/**
 * Created by kirill on 12.05.17.
 */

@LookScope
@Subcomponent(modules = {LooksModule.class})
public interface LookComponent {

    LooksPresenter provideLooksPresenter();

    void inject(CreationLookPresenter activity);

    void inject(CollagePresenter presenter);

    void inject(UpdateLookPresenter presenter);

}
