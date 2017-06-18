package com.kirill.kochnev.homewardrope.interactors;

import android.graphics.Bitmap;

import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.db.RepoResult;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.interactors.interfaces.ILooksInteractor;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractLookRepository;
import com.kirill.kochnev.homewardrope.repositories.utils.LooksByWardropeSpecification;
import com.kirill.kochnev.homewardrope.utils.ImageHelper;
import com.kirill.kochnev.homewardrope.utils.LookExeception;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;
import com.pushtorefresh.storio.sqlite.operations.put.PutResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public class LooksInteractor implements ILooksInteractor {

    private AbstractLookRepository looks;

    private Look look = new Look();

    public LooksInteractor(AbstractLookRepository looks) {
        this.looks = looks;
    }

    public Single<List<Look>> getLooks(long id) {
        return looks.query(id);
    }

    @Override
    public Single<DeleteResult> deleteLook(Look model) {
        ImageHelper.deleteImage(model.getFullImagePath(), model.getIconImagePath());
        return looks.deletItem(model);
    }

    @Override
    public Single<Look> getLook(long id) {
        return looks.getItem(id).map(look -> this.look = look);
    }

    @Override
    public Single<Look> getLook() {
        return Single.create(sub -> {
            if (look != null) {
                sub.onSuccess(look);
            } else {
                sub.onError(new Exception("No look was created"));
            }
        });
    }

    @Override
    public Single<RepoResult> saveLook(String name, String tag) {
        look.setName(name);
        look.setTag(tag);
        return looks.putItem(look);
    }

    @Override
    public Single<RepoResult> saveLookWithBitmap(String name, String tag, Bitmap bitmap) {
        return Single.create(sub -> {
            try {
                look.setName(name);
                look.setTag(tag);
                look.setFullImagePath(ImageHelper.createImageFile("look").getAbsolutePath());
                look.setIconImagePath(ImageHelper.createIconImageFile("look").getAbsolutePath());
                sub.onSuccess(look);
            } catch (Exception e) {
                sub.onError(e);
            }
        }).flatMap(o -> ImageHelper.saveImageAndIconObservable(look.getFullImagePath(), look.getIconImagePath(), bitmap))
                .flatMap(cropImg -> looks.putItem(look));
    }

    @Override
    public Single<HashSet<Long>> startCreation() {
        return Single.create(sub -> {
            int size = look.getThingIds().size();
            if (size >= AppConstants.MIN_COLLAGE_COUNT && size <= AppConstants.MAX_COLLAGE_COUNT) {
                sub.onSuccess(look.getThingIds());
            } else {
                sub.onError(new LookExeception("validation error, incompatable count of views", size <= AppConstants.MIN_COLLAGE_COUNT));
            }
        });
    }

    @Override
    public Single<List<Look>> getLooksByWardrope(long lastId, long wardropeId) {
        Single<List<Look>> single;
        single = looks.query(lastId);
        if (wardropeId != AppConstants.DEFAULT_ID) {
            single = looks.query(new LooksByWardropeSpecification(lastId, wardropeId));
        }
        return single;
    }

    @Override
    public void addThingId(long id) {
        Set<Long> thingsSet = look.getThingIds();
        int length = thingsSet.size();
        thingsSet.add(id);
        if (length == thingsSet.size()) {
            thingsSet.remove(id);
        }
    }

    @Override
    public void addWardropeId(long id) {
        look.setWardropeId(id);
    }

    public void initializeLook() {
        look = new Look();
    }

    @Override
    public void clear() {
        if (look != null) {
            look.getThingIds().clear();
            look.setWardropeId(null);
        }
    }
}
