package com.kirill.kochnev.homewardrope.interactors;

import android.util.Pair;

import com.kirill.kochnev.homewardrope.db.RepoResult;
import com.kirill.kochnev.homewardrope.db.models.Wardrope;
import com.kirill.kochnev.homewardrope.interactors.interfaces.IAddUpdateWardropeInteractor;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractWardropeRepository;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import org.jetbrains.annotations.NotNull;

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
    public Single<RepoResult> saveWardrope(String name, @NotNull HashSet<Long> thingIds, @NotNull HashSet<Long> lookIds) {
        wardrope.setName(name);
        wardrope.setThingsCount(thingIds.size());
        wardrope.setLooksCount(lookIds.size());
        return wardropes.putWardropeWithRelations(wardrope, thingIds, lookIds);
    }

    @Override
    public Pair<HashSet<Long>, HashSet<Long>> getStartIds() {
        return wardrope != null ? new Pair<>(wardrope.getLookIds(), wardrope.getThingIds()) : null;
    }
}
