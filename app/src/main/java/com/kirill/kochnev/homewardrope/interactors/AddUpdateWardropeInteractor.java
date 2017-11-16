package com.kirill.kochnev.homewardrope.interactors;

import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.db.RepoResult;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.repositories.WardropeRepository;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public class AddUpdateWardropeInteractor {

    private long id = AppConstants.DEFAULT_ID;
    private WardropeRepository wardropes;
    private Single<Wardrope> getWardropeSingle;

    @Inject
    public AddUpdateWardropeInteractor(WardropeRepository wardropes) {
        this.wardropes = wardropes;
    }

    public Single<Wardrope> getWardrope(long id) {
        this.id = id;
        getWardropeSingle = wardropes.getItem(id).cache();
        return getWardropeSingle;
    }

    public Single<Wardrope> getWardrope() {
        return getWardropeSingle;
    }

    public Single<RepoResult> saveWardrope(Wardrope wardrope) {
        if (id != AppConstants.DEFAULT_ID) {
            wardrope.setId(id);
        }
        return wardropes.putItem(wardrope);
    }
}
