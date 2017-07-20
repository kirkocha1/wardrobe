package com.kirill.kochnev.homewardrope.interactors;

import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.repositories.WardropeRepository;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public class WardropesInteractor {

    private WardropeRepository wardropes;

    public WardropesInteractor(WardropeRepository wardropes) {
        this.wardropes = wardropes;
    }

    public Single<DeleteResult> deleteWardropes(Wardrope model) {
        return wardropes.deletItem(model);
    }

    public Single<List<Wardrope>> getWardropes(long id) {
        return wardropes.query(id);
    }

    public Single<Wardrope> getWardrope(long id) {
        return wardropes.getItem(id);
    }
}
