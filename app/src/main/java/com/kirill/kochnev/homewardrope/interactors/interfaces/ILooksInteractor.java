package com.kirill.kochnev.homewardrope.interactors.interfaces;

import android.graphics.Bitmap;

import com.kirill.kochnev.homewardrope.db.models.Look;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import java.util.HashSet;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public interface ILooksInteractor {

    Single<List<Look>> getLooks(long id);

    Single<List<Look>> getLooksByWardrope(long id, long filterId);

    Single<DeleteResult> deleteLook(Look model);

    Single<Look> getLook(long id);

    Single<PutResult> saveLook(String name, String tag);

    Single<PutResult> saveLookWithBitmap(String name, String tag, Bitmap bitmap);

    Single<HashSet<Long>> startCreation();

    Single<Look> getLook();

    void addThingId(long id);

    void addWardropeId(long id);

    void clear();
}
