package com.kirill.kochnev.homewardrope.interactors;

import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.db.RepoResult;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.repositories.WardrobeRepository;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public class AddUpdateWardrobeInteractor {

    private long id = AppConstants.DEFAULT_ID;
    private WardrobeRepository wardrobes;
    private Single<Wardrope> getWardrobeSingle;

    @Inject
    public AddUpdateWardrobeInteractor(WardrobeRepository wardrobes) {
        this.wardrobes = wardrobes;
    }

    public Single<Wardrope> getWardrobe(long id) {
        this.id = id;
        getWardrobeSingle = wardrobes.getItem(id).cache();
        return getWardrobeSingle;
    }

    public Single<Wardrope> getWardrobe() {
        return getWardrobeSingle;
    }

    public Single<RepoResult> saveWardrobe(Wardrope wardrope) {
        if (id != AppConstants.DEFAULT_ID) {
            wardrope.setId(id);
        }
        return wardrobes.putItem(wardrope);
    }
}
