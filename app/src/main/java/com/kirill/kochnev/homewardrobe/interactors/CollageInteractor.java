package com.kirill.kochnev.homewardrobe.interactors;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.SparseArray;

import com.kirill.kochnev.homewardrobe.db.models.Thing;
import com.kirill.kochnev.homewardrobe.repositories.ThingRepository;
import com.kirill.kochnev.homewardrobe.repositories.utils.ThingsByIdsSpecofication;
import com.kirill.kochnev.homewardrobe.utils.ImageProcessor;

import java.util.HashSet;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public class CollageInteractor {

    private @NonNull final ThingRepository things;
    private @NonNull final ImageProcessor helper;

    @Inject
    public CollageInteractor(@NonNull final ThingRepository things, @NonNull final ImageProcessor helper) {
        this.things = things;
        this.helper = helper;
    }

    public Single<SparseArray<Bitmap>> getImages(HashSet<Long> thingIds) {
        return things
                .query(new ThingsByIdsSpecofication(thingIds))
                .map(list -> {
                    SparseArray<Bitmap> imageCache = new SparseArray<>();
                    int i = 0;
                    for (Thing thing : list) {
                        Bitmap bitmap = helper.makeImage(thing.getFullImagePath());
                        imageCache.put(i++, bitmap);
                    }
                    return imageCache;
                });
    }
}
