package com.kirill.kochnev.homewardrope.interactors;

import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.repositories.ThingRepository;
import com.kirill.kochnev.homewardrope.repositories.utils.ThingsByWardropeSpecification;
import com.kirill.kochnev.homewardrope.utils.ImageHelper;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;

import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public class ThingsInteractor {

    private ThingRepository things;
    private ImageHelper helper;

    @Inject
    public ThingsInteractor(ThingRepository things, ImageHelper helper) {
        this.things = things;
        this.helper = helper;
    }

    public Single<HashSet<Long>> getWardropeThingIds(long filterId) {
        return things.getWardropeThingIds(filterId);
    }

    public Single<List<Thing>> getThingsByWardrope(long lastId, long wardropeId) {
        Single<List<Thing>> single;
        single = things.query(lastId);
        if (wardropeId != AppConstants.DEFAULT_ID) {
            single = things.query(new ThingsByWardropeSpecification(lastId, wardropeId));
        }
        return single;
    }

    public Single<Thing> getThing(long id) {
        return things.getItem(id);
    }

    public Single<DeleteResult> deleteThings(Thing model) {
        helper.deleteImage(model.getFullImagePath(), model.getIconImagePath());
        return things.deleteItem(model);
    }
}
