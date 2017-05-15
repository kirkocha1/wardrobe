package com.kirill.kochnev.homewardrope.mvp.presenters.base;

import com.kirill.kochnev.homewardrope.db.models.IDbModel;
import com.kirill.kochnev.homewardrope.mvp.views.base.IPaginationView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kirill Kochnev on 12.02.17.
 */

public abstract class BaseDbListPresenter<V extends IPaginationView> extends BaseMvpPresenter<V> {
    public abstract void loadMoreData(long lastId);

    public abstract void onLongItemClick(IDbModel model);

    public abstract void onItemClick(IDbModel model);

    protected abstract void refreshList();

    private void dropList() {
        getViewState().dropData();
    }

    @Override
    public void attachView(V view) {
        super.attachView(view);
        refreshList();
    }

    @Override
    public void detachView(V view) {
        dropList();
        super.detachView(view);

    }

    public <T> Disposable getListDisposable(Single<List<T>> obsevable, @NotNull Consumer<List<T>> onSuccess, @NotNull Consumer<Throwable> onError) {
        return obsevable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError);
    }
}
