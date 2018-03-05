package com.kirill.kochnev.homewardrope.interactors;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.db.RepoResult;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.repositories.LookRepository;
import com.kirill.kochnev.homewardrope.repositories.utils.LooksByWardrobeSpecification;
import com.kirill.kochnev.homewardrope.utils.ImageHelper;
import com.kirill.kochnev.homewardrope.utils.LookExeception;
import com.kirill.kochnev.homewardrope.utils.bus.IdBus;
import com.kirill.kochnev.homewardrope.utils.bus.StateBus;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public class LooksInteractor {

    private @NonNull final LookRepository looks;
    private @NonNull final ImageHelper helper;
    private @NonNull IdBus idBus;
    private @NonNull StateBus stateBus;

    private Look look = new Look();

    @Inject
    LooksInteractor(
            @NonNull LookRepository looks,
            @NonNull ImageHelper helper,
            @NonNull IdBus idBus,
            @NonNull StateBus stateBus
    ) {
        this.looks = looks;
        this.helper = helper;
        this.idBus = idBus;
        this.stateBus = stateBus;
    }

    public Single<DeleteResult> deleteLook(Look model) {
        helper.deleteImage(model.getFullImagePath(), model.getIconImagePath());
        return looks
                .deleteItem(model);

    }

    public Single<Look> getLook(long id) {
        return looks
                .getItem(id)
                .map(look -> {
                    this.look = look;
                    look.setBitmap(helper.makeImage(look.getFullImagePath()));
                    return look;
                });
    }

    public Single<Look> getLook() {
        return Single.just(look);
    }

    public Single<RepoResult> saveLook(String name, String tag) {
        look.setName(name);
        look.setTag(tag);
        return looks
                .putItem(look);
    }

    public Single<RepoResult> saveLookWithBitmap(String name, String tag, Bitmap bitmap) {
        return Single
                .fromCallable(() -> {
                    look.setName(name);
                    look.setTag(tag);
                    look.setFullImagePath(helper.createImageFile("look").getAbsolutePath());
                    look.setIconImagePath(helper.createIconImageFile("look").getAbsolutePath());
                    return look;
                })
                .flatMap(o -> helper.saveImageAndIconObservable(look.getFullImagePath(), look.getIconImagePath(), bitmap))
                .flatMap(cropImg -> looks.putItem(look));
    }

    public Single<HashSet<Long>> startCreation() {
        return Single
                .fromCallable(() -> {
                    int size = look.getThingIds().size();
                    boolean isSizeIsValid = size >= AppConstants.MIN_COLLAGE_COUNT && size <= AppConstants.MAX_COLLAGE_COUNT;
                    if (!isSizeIsValid) {
                        throw new LookExeception("validation error, incompatable count of views", size <= AppConstants.MIN_COLLAGE_COUNT);
                    }
                    return look.getThingIds();
                });
    }

    public Single<List<Look>> getLooksByWardrope(long lastId, long wardropeId) {
        Single<List<Look>> single;
        single = looks.query(lastId);
        if (wardropeId != AppConstants.DEFAULT_ID) {
            single = looks.query(new LooksByWardrobeSpecification(lastId, wardropeId));
        }
        return single;
    }

    public void addThingId(long id) {
        Set<Long> thingsSet = look.getThingIds();
        int length = thingsSet.size();
        thingsSet.add(id);
        if (length == thingsSet.size()) {
            thingsSet.remove(id);
        }
    }


    public Observable<Pair<ViewMode, Boolean>> observeEditableModeChanges() {
        return stateBus.getBus();
    }

    public Observable<Pair<ViewMode, Long>> observeWardrobeIdOrThingsId() {
        return idBus.getBus();
    }

    public Completable sendLookIdWithMode(ViewMode mode, long lookId) {
        return Completable.fromAction(() -> idBus.passData(new Pair<>(mode, lookId)));
    }

    public void addWardropeId(Long id) {
        look.setWardropeId(id);
    }

    public void initializeLook() {
        look = new Look();
    }

    public void clear() {
        if (look != null) {
            look.getThingIds().clear();
        }
    }
}
