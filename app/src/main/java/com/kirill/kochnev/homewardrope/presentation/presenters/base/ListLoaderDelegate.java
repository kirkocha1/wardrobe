package com.kirill.kochnev.homewardrope.presentation.presenters.base;

import android.support.annotation.NonNull;
import android.util.Log;

import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.presentation.views.base.IPaginationView;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ListLoaderDelegate {

    public static final String TAG = "BaseDbPresenter";
    @NonNull
    private final IPaginationView view;

    public ListLoaderDelegate(@NonNull IPaginationView view) {
        this.view = view;
    }

    public <T> Disposable getDisposable(Single<T> observable, @NonNull Consumer<T> onSuccess, @NonNull Consumer<Throwable> onError) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError);
    }

    public <T extends IDbModel> Disposable getListDisposable(Single<List<T>> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        list -> view.onLoadFinished(list),
                        e -> Log.e(TAG, "refreshList: " + e.getMessage())
                );
    }
}
