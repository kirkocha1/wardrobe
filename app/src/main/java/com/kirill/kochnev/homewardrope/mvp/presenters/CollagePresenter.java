package com.kirill.kochnev.homewardrope.mvp.presenters;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseArray;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.enums.CollageMode;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseMvpPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.ICollageView;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractThingRepository;
import com.kirill.kochnev.homewardrope.repositories.utils.ThingsByIdsSpecofication;
import com.kirill.kochnev.homewardrope.utils.ImageHelper;

import java.util.HashSet;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kirill on 27.04.17.
 */

@InjectViewState
public class CollagePresenter extends BaseMvpPresenter<ICollageView> {

    public static final String TAG = "CollagePresenter";
    private SparseArray<Bitmap> imageCache = new SparseArray<>();

    @Inject
    protected AbstractThingRepository things;

    public CollagePresenter(HashSet<Long> thingIds) {
        WardropeApplication.getComponent().inject(this);
        things.query(new ThingsByIdsSpecofication(thingIds))
                .map(list -> {
                    int i = 0;
                    for (Thing thing : list) {
                        Bitmap bitmap = ImageHelper.makeImage(thing.getFullImagePath());
                        imageCache.put(i, bitmap);
                        i++;
                    }
                    return new Object();
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> {
                    getViewState().constructView(imageCache, CollageMode.getByNum(imageCache.size()));
                }, e -> Log.e(TAG, e.getMessage()));
    }

}
