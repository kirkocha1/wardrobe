package com.kirill.kochnev.homewardrobe.presentation.presenters.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class CompositeDisposableDelegate {

    private @Nullable CompositeDisposable compositeDisposable;

    public void addToCompositeDisposable(@NonNull Disposable subscription) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(subscription);
    }

    public void unsubscribe() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

}
