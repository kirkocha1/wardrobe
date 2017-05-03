package com.kirill.kochnev.homewardrope.mvp.presenters;

import android.graphics.Bitmap;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.enums.CreationLookState;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseMvpPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IFirstStepCreationLookView;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractLookRepository;
import com.kirill.kochnev.homewardrope.utils.ImageHelper;

import java.util.Set;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kirill on 27.04.17.
 */

@InjectViewState
public class CreationLookPresenter extends BaseMvpPresenter<IFirstStepCreationLookView> {
    public static final String TAG = "CreationLook";
    private Look model;

    @Inject
    AbstractLookRepository looks;

    public CreationLookPresenter() {
        WardropeApplication.getComponent().inject(this);
        model = new Look();
    }

    public void addThingId(long id) {
        Set<Long> thingsSet = model.getThingIds();
        int length = thingsSet.size();
        thingsSet.add(id);
        if (length == thingsSet.size()) {
            thingsSet.remove(id);
        }
    }

    public void clearIds() {
        if (model != null) {
            model.getThingIds().clear();
        }
    }

    public void processLook(String name, String tag, Bitmap bitmap) {
        try {
            model.setName(name);
            model.setTag(tag);
            model.setFullImagePath(ImageHelper.createImageFile("look").getAbsolutePath());
            model.setIconImagePath(ImageHelper.createIconImageFile("look").getAbsolutePath());
            ImageHelper.saveImageAndIconObservable(model.getFullImagePath(), model.getIconImagePath(), bitmap)
                    .flatMap(cropImg -> looks.putItem(model))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(o -> getViewState().onSuccess());
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage());
        }
    }

    public void resolveBtnsState(CreationLookState state) {
        switch (state) {
            case ALL_THINGS:
            case WARDROPES:
                getViewState().setBtnsState(true, false, false);
                break;
            case START:
                getViewState().setBtnsState(false, false, true);
                break;
            case COLLAGE:
                getViewState().setBtnsState(false, true, false);
                break;
        }
    }

    public void startCreationProcess() {
        int size = model.getThingIds().size();
        if (size >= AppConstants.MIN_COLLAGE_COUNT && size <= AppConstants.MAX_COLLAGE_COUNT) {
            getViewState().openCollageFragment(model.getThingIds());
        } else {
            getViewState().showError(size <= AppConstants.MIN_COLLAGE_COUNT);
        }
    }
}
