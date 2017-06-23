package com.kirill.kochnev.homewardrope.utils.bus;

import android.util.Pair;

import com.kirill.kochnev.homewardrope.enums.ViewMode;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Kirill Kochnev on 15.05.17.
 */

public class EditStateBus implements StateBus {

    private PublishSubject<Pair<ViewMode, Boolean>> bus = PublishSubject.create();

    @Override
    public Observable<Pair<ViewMode, Boolean>> getBus() {
        return bus;
    }

    @Override
    public void passData(Pair<ViewMode, Boolean> data) {
        bus.onNext(data);
    }

    @Override
    public Disposable register(Consumer<Pair<ViewMode, Boolean>> listener) {
        return bus.subscribe(listener);
    }
}
