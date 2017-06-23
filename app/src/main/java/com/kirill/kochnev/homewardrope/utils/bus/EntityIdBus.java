package com.kirill.kochnev.homewardrope.utils.bus;

import android.util.Pair;

import com.kirill.kochnev.homewardrope.enums.ViewMode;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by kirill on 12.05.17.
 */

public class EntityIdBus implements IdBus {

    private PublishSubject<Pair<ViewMode, Long>> bus = PublishSubject.create();

    @Override
    public Observable<Pair<ViewMode, Long>> getBus() {
        return bus;
    }

    @Override
    public void passData(Pair<ViewMode, Long> idPair) {
        bus.onNext(idPair);
    }

    @Override
    public Disposable register(Consumer<Pair<ViewMode, Long>> listener) {
        return bus.subscribe(listener);
    }
}
