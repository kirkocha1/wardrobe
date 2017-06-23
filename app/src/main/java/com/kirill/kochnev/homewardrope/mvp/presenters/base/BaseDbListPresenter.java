package com.kirill.kochnev.homewardrope.mvp.presenters.base;

import android.util.Log;

import com.kirill.kochnev.homewardrope.AppConstants;
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
    public static final String TAG = "BaseDbPresenter";

    public abstract void loadMoreData(long lastId);

    public abstract void onLongItemClick(IDbModel model);

    public abstract void onItemClick(IDbModel model);

    public abstract void addOrUpdateListItem(long id);

    @Override
    protected void onFirstViewAttach() {
        loadMoreData(AppConstants.DEFAULT_ID);
    }

    public <T> Disposable getDisposable(Single<T> obsevable, @NotNull Consumer<T> onSuccess, @NotNull Consumer<Throwable> onError) {
        return obsevable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError);
    }

    public <T> Disposable getListDisposable(Single<List<T>> obsevable) {
        return obsevable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> getViewState().onLoadFinished(list), e -> Log.e(TAG, "refreshList: " + e.getMessage()));
    }
}
