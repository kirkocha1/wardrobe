package com.kirill.kochnev.homewardrope.utils.bus;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by kirill on 12.05.17.
 */

public class RxBus implements IdBus {

    private PublishSubject<Long> bus = PublishSubject.create();

    @Override
    public void passData(Long id) {
        bus.onNext(id);
    }

    @Override
    public Disposable register(Consumer<Long> listener) {
        return bus.subscribe(listener);
    }

    public Observable<Long> getBus() {
        return bus;
    }

}
