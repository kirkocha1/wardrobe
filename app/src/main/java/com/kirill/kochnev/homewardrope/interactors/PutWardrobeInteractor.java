package com.kirill.kochnev.homewardrope.interactors;

import android.support.annotation.NonNull;
import android.util.Pair;

import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.db.RepoResult;
import com.kirill.kochnev.homewardrope.db.models.Wardrobe;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.repositories.WardrobeRepository;
import com.kirill.kochnev.homewardrope.utils.bus.IdBus;
import com.kirill.kochnev.homewardrope.utils.bus.StateBus;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kirill on 11.05.17.
 */

public class PutWardrobeInteractor {

    private long id = AppConstants.DEFAULT_ID;
    private @NonNull final WardrobeRepository wardrobes;
    private @NonNull final IdBus idBus;
    private @NonNull final StateBus stateBus;

    private Single<Wardrobe> getWardrobeSingle;

    @Inject
    PutWardrobeInteractor(
            @NonNull WardrobeRepository wardrobes,
            @NonNull IdBus idBus,
            @NonNull StateBus stateBus
    ) {
        this.wardrobes = wardrobes;
        this.idBus = idBus;
        this.stateBus = stateBus;
    }

    public Single<Wardrobe> getWardrobe(long id) {
        this.id = id;
        getWardrobeSingle = wardrobes.getItem(id).cache();
        return getWardrobeSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Wardrobe> getWardrobe() {
        return getWardrobeSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<RepoResult> saveWardrobe(Wardrobe wardrobe) {
        if (id != AppConstants.DEFAULT_ID) {
            wardrobe.setId(id);
        }
        return wardrobes
                .putItem(wardrobe)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Pair<ViewMode, Long>> observeThingIds() {
        return idBus.getBus();
    }

    public Completable changeEditableMode(@NonNull final Pair<ViewMode, Boolean> data) {
        return Completable.fromAction(() -> stateBus.passData(data));

    }
}
