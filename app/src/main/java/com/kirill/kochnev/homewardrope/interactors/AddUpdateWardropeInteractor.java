package com.kirill.kochnev.homewardrope.interactors;

import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.db.RepoResult;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.interactors.interfaces.IAddUpdateWardropeInteractor;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractWardropeRepository;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public class AddUpdateWardropeInteractor implements IAddUpdateWardropeInteractor {

    private long id = AppConstants.DEFAULT_ID;
    private AbstractWardropeRepository wardropes;
    private Single<Wardrope> getWardropeSingle;

    public AddUpdateWardropeInteractor(AbstractWardropeRepository wardropes) {
        this.wardropes = wardropes;
    }

    @Override
    public Single<Wardrope> getWardrope(long id) {
        this.id = id;
        getWardropeSingle = wardropes.getItem(id).cache();
        return getWardropeSingle;
    }

    @Override
    public Single<Wardrope> getWardrope() {
        return getWardropeSingle;
    }

    @Override
    public Single<RepoResult> saveWardrope(Wardrope wardrope) {
        if (id != AppConstants.DEFAULT_ID) {
            wardrope.setId(id);
        }
        return wardropes.putItem(wardrope);
    }
}
