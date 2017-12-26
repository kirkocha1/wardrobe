package com.kirill.kochnev.homewardrope.interactors;

import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.repositories.WardrobeRepository;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public class WardrobesInteractor {

    private WardrobeRepository wardrobes;

    @Inject
    public WardrobesInteractor(WardrobeRepository wardrobes) {
        this.wardrobes = wardrobes;
    }

    public Single<DeleteResult> deleteWardropes(Wardrope model) {
        return wardrobes.deleteItem(model);
    }

    public Single<List<Wardrope>> getWardrobes(long id) {
        return wardrobes.query(id);
    }

    public Single<Wardrope> getWardrobe(long id) {
        return wardrobes.getItem(id);
    }
}
