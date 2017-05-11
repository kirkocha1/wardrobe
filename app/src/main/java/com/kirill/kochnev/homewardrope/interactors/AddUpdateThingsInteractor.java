package com.kirill.kochnev.homewardrope.interactors;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.interactors.interfaces.IAddUpdateThingsInteractor;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractThingRepository;
import com.kirill.kochnev.homewardrope.utils.ImageHelper;

import java.io.File;
import java.io.IOException;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public class AddUpdateThingsInteractor implements IAddUpdateThingsInteractor {
    public static final String TAG = "UpdateThingsInteractor";
    private Thing thing = new Thing();

    protected AbstractThingRepository things;

    public AddUpdateThingsInteractor(AbstractThingRepository things) {
        this.things = things;
    }

    public Single<Thing> getThing(long id) {
        return things.getItem(id).map(thing -> this.thing = thing);
    }

    @Override
    public Single<Boolean> saveThing(String name, String tag) {
        thing.setName(name);
        thing.setTag(tag);
        return things.putItem(thing);
    }

    @Override
    public Single<Uri> getPhotoUri() {
        return Single.create(sub -> {
            File photoFile = null;
            try {
                photoFile = ImageHelper.createImageFile("thing");
                thing.setFullImagePath(photoFile.getAbsolutePath());
                thing.setIconImagePath(ImageHelper.createIconImageFile("thing").getAbsolutePath());
            } catch (IOException ex) {
                Log.e(TAG, "problems with creating image uri, error: " + ex.getMessage());
                sub.onError(ex);
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(WardropeApplication.getContext(), "com.kirill.kochnev.homewardrope", photoFile);
                sub.onSuccess(photoURI);
            }
        });
    }

    public Single<Bitmap> saveImages() {
        return ImageHelper.getAndSaveCropImageObservable(thing.getFullImagePath(), thing.getIconImagePath());
    }
}
