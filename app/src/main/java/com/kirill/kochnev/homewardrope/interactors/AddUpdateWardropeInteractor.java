package com.kirill.kochnev.homewardrope.interactors;

import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.interactors.interfaces.IAddUpdateWardropeInteractor;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractWardropeRepository;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import java.util.HashSet;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public class AddUpdateWardropeInteractor implements IAddUpdateWardropeInteractor {

    private Wardrope wardrope = new Wardrope();

    private AbstractWardropeRepository wardropes;

    public AddUpdateWardropeInteractor(AbstractWardropeRepository wardropes) {
        this.wardropes = wardropes;
    }

    @Override
    public Single<Wardrope> getWardrope(long id) {
        return wardropes.getItem(id).map(wardrope -> this.wardrope = wardrope);
    }

    @Override
    public Single<PutResult> saveWardrope(String name, HashSet<Long> thingIds) {
        wardrope.setName(name);
        wardrope.setThingsCount(thingIds.size());
        return wardropes.putWardropeWithThings(wardrope, thingIds);
    }
}
