package com.kirill.kochnev.homewardrope.interactors.interfaces;

import android.graphics.Bitmap;

import com.kirill.kochnev.homewardrope.db.models.Look;

import java.util.HashSet;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public interface ILooksInteractor {

    Single<List<Look>> getLooks(long id);

    Single<Boolean> deleteLook(Look model);

    Single<Look> getLook(long id);

    Single<Boolean> saveLook(String name, String tag);

    Single<Boolean> saveLookWithBitmap(String name, String tag, Bitmap bitmap);

    Single<HashSet<Long>> startCreation();

    Single<Look> getLook();

    void addThingId(long id);

    void clear();
}
