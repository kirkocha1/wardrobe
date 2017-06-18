package com.kirill.kochnev.homewardrope.mvp.presenters.thing;

import android.content.Intent;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.interactors.interfaces.IAddUpdateThingsInteractor;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseMvpPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IAddUpdateThingView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.kirill.kochnev.homewardrope.utils.ImageHelper.makeImage;


@InjectViewState
public class AddUpdateThingPresenter extends BaseMvpPresenter<IAddUpdateThingView> {

    public static final String TAG = "AddUpdateThingPresenter";

    @Inject
    IAddUpdateThingsInteractor interactor;

    public AddUpdateThingPresenter(long id) {
        WardropeApplication.getComponent().inject(this);
        initValues(id);
    }


    private void initValues(long id) {
        unsubscribeOnDestroy(interactor.getThing(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> getViewState().updateView(model.getName(), model.getTag(), makeImage(model.getFullImagePath())),
                        e -> Log.e(TAG, e.getMessage())));
    }

    public void saveThing(String name, String tag) {
        unsubscribeOnDestroy(interactor.saveThing(name, tag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    Intent intent = new Intent();
                    intent.putExtra(AppConstants.ADD_UPDATED_ID, result.getId());
                    getViewState().onSave(intent);
                }, e -> Log.e(TAG, e.getMessage())));
    }

    public void createUri() {
        interactor.getPhotoUri()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uri -> getViewState().sendMakePhotoIntent(uri));
    }

    public void processImage() {
        unsubscribeOnDestroy(interactor.saveImages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(img -> getViewState().setImage(img), ex -> getViewState().showError(ex.getMessage())));
    }

}
