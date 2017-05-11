package com.kirill.kochnev.homewardrope.interactors;

import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.interactors.interfaces.IWardropesInteractor;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractWardropeRepository;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public class WardropesInteractor implements IWardropesInteractor {

    private AbstractWardropeRepository wardropes;

    public WardropesInteractor(AbstractWardropeRepository wardropes) {
        this.wardropes = wardropes;
    }

    @Override
    public Single<Boolean> deleteWardropes(Wardrope model) {
        return wardropes.deletItem(model);
    }

    @Override
    public Single<List<Wardrope>> getWardropes(long id) {
        return wardropes.query(id);
    }
}
