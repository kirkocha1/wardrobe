package com.kirill.kochnev.homewardrope.interactors;

import android.graphics.Bitmap;
import android.util.SparseArray;

import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.interactors.interfaces.ICollageInteractor;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractThingRepository;
import com.kirill.kochnev.homewardrope.repositories.utils.ThingsByIdsSpecofication;
import com.kirill.kochnev.homewardrope.utils.ImageHelper;

import java.util.HashSet;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public class CollageInteractor implements ICollageInteractor {

    protected AbstractThingRepository things;

    public CollageInteractor(AbstractThingRepository things) {
        this.things = things;
    }

    @Override
    public Single<SparseArray<Bitmap>> getImages(HashSet<Long> thingIds) {
        return things.query(new ThingsByIdsSpecofication(thingIds))
                .map(list -> {
                    SparseArray<Bitmap> imageCache = new SparseArray<>();
                    int i = 0;
                    for (Thing thing : list) {
                        Bitmap bitmap = ImageHelper.makeImage(thing.getFullImagePath());
                        imageCache.put(i++, bitmap);
                    }
                    return imageCache;
                });
    }
}