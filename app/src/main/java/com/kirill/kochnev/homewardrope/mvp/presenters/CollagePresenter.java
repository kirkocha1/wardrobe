package com.kirill.kochnev.homewardrope.mvp.presenters;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.enums.CollageMode;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseMvpPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.ICollageView;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractThingRepository;
import com.kirill.kochnev.homewardrope.repositories.utils.ThingsByIdsSpecofication;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kirill on 27.04.17.
 */

@InjectViewState
public class CollagePresenter extends BaseMvpPresenter<ICollageView> {

    public static final String TAG = "CollagePresenter";

    @Inject
    protected AbstractThingRepository things;

    private List<String> paths = new ArrayList<>();

    public CollagePresenter(HashSet<Long> thingIds) {
        WardropeApplication.getComponent().inject(this);
        things.query(new ThingsByIdsSpecofication(thingIds))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    getViewState().constructView(list, CollageMode.getByNum(list.size()));
                }, e -> Log.e(TAG, e.getMessage()));

    }

}
