package com.kirill.kochnev.homewardrope.interactors;

import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.db.RepoResult;
import com.kirill.kochnev.homewardrope.db.models.Wardrobe;
import com.kirill.kochnev.homewardrope.repositories.WardrobeRepository;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public class AddUpdateWardrobeInteractor {

    private long id = AppConstants.DEFAULT_ID;
    private WardrobeRepository wardrobes;
    private Single<Wardrobe> getWardrobeSingle;

    @Inject
    public AddUpdateWardrobeInteractor(WardrobeRepository wardrobes) {
        this.wardrobes = wardrobes;
    }

    public Single<Wardrobe> getWardrobe(long id) {
        this.id = id;
        getWardrobeSingle = wardrobes.getItem(id).cache();
        return getWardrobeSingle;
    }

    public Single<Wardrobe> getWardrobe() {
        return getWardrobeSingle;
    }

    public Single<RepoResult> saveWardrobe(Wardrobe wardrobe) {
        if (id != AppConstants.DEFAULT_ID) {
            wardrobe.setId(id);
        }
        return wardrobes.putItem(wardrobe);
    }
}
