package com.kirill.kochnev.homewardrope.interactors;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.kirill.kochnev.homewardrope.db.RepoResult;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.repositories.ThingRepository;
import com.kirill.kochnev.homewardrope.utils.ImageProcessor;

import java.io.File;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public class PutThingsInteractor {
    public static final String TAG = "UpdateThingsInteractor";

    private Thing thing = new Thing();

    private @NonNull final ThingRepository things;
    private @NonNull final ImageProcessor helper;

    @Inject
    PutThingsInteractor(@NonNull final ImageProcessor helper, @NonNull final ThingRepository things) {
        this.things = things;
        this.helper = helper;
    }

    public Single<Thing> getThing(long id) {
        return things
                .getItem(id)
                .map(thing -> {
                    this.thing = thing;
                    thing.setBitmap(helper.makeImage(thing.getFullImagePath()));
                    return thing;
                });
    }

    public Single<RepoResult> saveThing(String name, String tag) {
        thing.setName(name);
        thing.setTag(tag);
        return things
                .putItem(thing);
    }

    public Single<Uri> getPhotoUri() {
        return Single
                .fromCallable(() -> {
                    File photoFile = helper.createImageFile("thing");
                    thing.setFullImagePath(photoFile.getAbsolutePath());
                    thing.setIconImagePath(helper.createIconImageFile("thing").getAbsolutePath());
                    return Uri.fromFile(photoFile);
                });
    }

    public Single<Bitmap> saveImages() {
        return helper
                .getAndSaveCropImageObservable(thing.getFullImagePath(), thing.getIconImagePath());
    }
}