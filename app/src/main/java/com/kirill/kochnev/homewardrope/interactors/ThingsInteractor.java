package com.kirill.kochnev.homewardrope.interactors;

import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.interactors.interfaces.IThingInteractor;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractThingRepository;
import com.kirill.kochnev.homewardrope.repositories.utils.ThingsByWardropeSpecification;
import com.kirill.kochnev.homewardrope.utils.ImageHelper;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;

import java.util.HashSet;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public class ThingsInteractor implements IThingInteractor {

    protected AbstractThingRepository things;

    public ThingsInteractor(AbstractThingRepository things) {
        this.things = things;
    }

    @Override
    public Single<HashSet<Long>> getWardropeThingIds(long filterId) {
        return things.getWardropeThingIds(filterId);
    }

    @Override
    public Single<List<Thing>> getThingsByWardrope(long lastId, long wardropeId) {
        Single<List<Thing>> single;
        single = things.query(lastId);
        if (wardropeId != AppConstants.DEFAULT_ID) {
            single = things.query(new ThingsByWardropeSpecification(lastId, wardropeId));
        }
        return single;
    }

    @Override
    public Single<Thing> getThing(long id) {
        return things.getItem(id);
    }

    @Override
    public Single<DeleteResult> deleteThings(Thing model) {
        ImageHelper.deleteImage(model.getFullImagePath(), model.getIconImagePath());
        return things.deletItem(model);
    }
}
