package com.kirill.kochnev.homewardrope.interactors;

import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.interactors.interfaces.ILooksInteractor;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractLookRepository;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public class LooksInteractor implements ILooksInteractor {

    private AbstractLookRepository looks;

    public LooksInteractor(AbstractLookRepository looks) {
        this.looks = looks;
    }

    public Single<List<Look>> getLooks(long id) {
        return looks.query(id);
    }

    @Override
    public Single<Boolean> deleteLook(Look model) {
        return looks.deletItem(model);
    }


}
