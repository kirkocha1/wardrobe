package com.kirill.kochnev.homewardrope.interactors;

import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.interactors.interfaces.IWardropesInteractor;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractWardropeRepository;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;

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
    public Single<DeleteResult> deleteWardropes(Wardrope model) {
        return wardropes.deletItem(model);
    }

    @Override
    public Single<List<Wardrope>> getWardropes(long id) {
        return wardropes.query(id);
    }

    @Override
    public Single<Wardrope> getWardrope(long id) {
        return wardropes.getItem(id);
    }
}
