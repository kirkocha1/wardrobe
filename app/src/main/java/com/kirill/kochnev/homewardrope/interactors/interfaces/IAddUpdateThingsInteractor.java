package com.kirill.kochnev.homewardrope.interactors.interfaces;

import android.graphics.Bitmap;
import android.net.Uri;

import com.kirill.kochnev.homewardrope.db.RepoResult;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public interface IAddUpdateThingsInteractor {

    Single<Thing> getThing(long id);

    Single<RepoResult> saveThing(String name, String tag);

    Single<Uri> getPhotoUri();

    Single<Bitmap> saveImages();

}
