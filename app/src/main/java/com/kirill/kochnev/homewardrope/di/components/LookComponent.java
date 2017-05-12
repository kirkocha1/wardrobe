package com.kirill.kochnev.homewardrope.di.components;

import com.kirill.kochnev.homewardrope.di.modules.LookInteractorModule;
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
@Subcomponent(modules = {LookInteractorModule.class})
public interface LookComponent {

    void inject(LooksPresenter presenter);

    void inject(CreationLookPresenter activity);

    void inject(CollagePresenter presenter);

    void inject(UpdateLookPresenter presenter);

}
