package com.kirill.kochnev.homewardrope.utils.bus;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by kirill on 12.05.17.
 */

public interface IBus<T> {

    Observable<T> getBus();

    void passData(T data);

    Disposable register(Consumer<T> listener);
}
