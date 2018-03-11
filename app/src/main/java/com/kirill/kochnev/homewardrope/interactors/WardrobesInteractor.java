package com.kirill.kochnev.homewardrope.interactors;

import android.support.annotation.NonNull;
import android.util.Pair;

import com.kirill.kochnev.homewardrope.db.models.Wardrobe;
import com.kirill.kochnev.homewardrope.enums.ViewMode;
import com.kirill.kochnev.homewardrope.repositories.WardrobeRepository;
import com.kirill.kochnev.homewardrope.utils.bus.IdBus;
import com.pushtorefresh.storio.sqlite.operations.delete.DeleteResult;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by kirill on 11.05.17.
 */

public class WardrobesInteractor {

    private @NonNull final WardrobeRepository wardrobes;
    private @NonNull final IdBus bus;

    @Inject
    WardrobesInteractor(@NonNull final WardrobeRepository wardrobes, @NonNull final IdBus bus) {
        this.wardrobes = wardrobes;
        this.bus = bus;
    }

    public Single<DeleteResult> deleteWardropes(Wardrobe model) {
        return wardrobes
                .deleteItem(model);
    }

    public Single<List<Wardrobe>> getWardrobes(long id) {
        return wardrobes
                .query(id);
    }

    public Single<Wardrobe> getWardrobe(long id) {
        return wardrobes
                .getItem(id);
    }

    public Completable passClickedWardrobeData(@NonNull final Pair<ViewMode, Long> data) {
        return Completable.fromAction(() -> bus.passData(data));
    }
}
