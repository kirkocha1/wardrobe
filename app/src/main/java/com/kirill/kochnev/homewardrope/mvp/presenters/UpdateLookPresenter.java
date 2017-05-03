package com.kirill.kochnev.homewardrope.mvp.presenters;

import android.graphics.Bitmap;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseMvpPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IUpdateLook;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractLookRepository;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.kirill.kochnev.homewardrope.utils.ImageHelper.makeImage;

/**
 * Created by kirill on 03.05.17.
 */

@InjectViewState
public class UpdateLookPresenter extends BaseMvpPresenter<IUpdateLook> {

    private Look model;

    @Inject
    protected AbstractLookRepository looks;

    public UpdateLookPresenter(long lookId) {
        WardropeApplication.getComponent().inject(this);
        if (lookId != AppConstants.DEFAULT_ID) {
            init(lookId);
        }
    }

    private void init(long lookId) {
        looks.getItem(lookId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(look -> {
                    if (look != null) {
                        model = look;
                        Bitmap map = makeImage(look.getFullImagePath());
                        getViewState().setLookData(look);
                    }
                });
    }
}
