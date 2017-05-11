package com.kirill.kochnev.homewardrope.interactors;

import android.graphics.Bitmap;

import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.interactors.interfaces.ILooksInteractor;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractLookRepository;
import com.kirill.kochnev.homewardrope.utils.ImageHelper;
import com.kirill.kochnev.homewardrope.utils.LookExeception;

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
    public Single<Boolean> deleteLook(Look model) {
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
    public Single<Boolean> saveLook(String name, String tag) {
        look.setName(name);
        look.setTag(tag);
        return looks.putItem(look);
    }

    @Override
    public Single<Boolean> saveLookWithBitmap(String name, String tag, Bitmap bitmap) {
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
    public void addThingId(long id) {
        Set<Long> thingsSet = look.getThingIds();
        int length = thingsSet.size();
        thingsSet.add(id);
        if (length == thingsSet.size()) {
            thingsSet.remove(id);
        }
    }

    @Override
    public void clear() {
        if (look != null) {
            look.getThingIds().clear();
        }
    }
}
