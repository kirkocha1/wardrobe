package com.kirill.kochnev.homewardrope.interactors;

import android.support.annotation.NonNull;
import android.util.Pair;

import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.repositories.ThingRepository;
import com.kirill.kochnev.homewardrope.repositories.utils.ThingsByWardrobeSpecification;
import com.kirill.kochnev.homewardrope.utils.ImageProcessor;
import com.kirill.kochnev.homewardrope.utils.bus.IdBus;
import com.kirill.kochnev.homewardrope.utils.bus.StateBus;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;

import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public class ThingsInteractor {

    private @NonNull final ThingRepository things;
    private @NonNull final ImageProcessor helper;
    private @NonNull IdBus idBus;
    private @NonNull StateBus stateBus;

    @Inject
    ThingsInteractor(
            @NonNull ThingRepository things,
            @NonNull ImageProcessor helper,
            @NonNull IdBus idBus,
            @NonNull StateBus stateBus
    ) {
        this.things = things;
        this.helper = helper;
        this.idBus = idBus;
        this.stateBus = stateBus;
    }

    public Single<HashSet<Long>> getWardrobeThingIds(long filterId) {
        return things
                .getWardropeThingIds(filterId);
    }

    public Single<List<Thing>> getThingsByWardrobe(long lastId, long wardrobeId) {
        Single<List<Thing>> single;
        single = things.query(lastId);
        if (wardrobeId != AppConstants.DEFAULT_ID) {
            single = things.query(new ThingsByWardrobeSpecification(lastId, wardrobeId));
        }
        return single;
    }

    public Observable<Pair<ViewMode, Boolean>> observeEditableModeChanges() {
        return stateBus.getBus();
    }

    public Completable sendThingIdWithMode(ViewMode mode, long thingId) {
        return Completable.fromAction(() -> idBus.passData(new Pair<>(mode, thingId)));
    }

    public Single<Thing> getThing(long id) {
        return things.getItem(id);
    }

    public Single<DeleteResult> deleteThings(Thing model) {
        helper.deleteImage(model.getFullImagePath(), model.getIconImagePath());
        return things.deleteItem(model);
    }
}
