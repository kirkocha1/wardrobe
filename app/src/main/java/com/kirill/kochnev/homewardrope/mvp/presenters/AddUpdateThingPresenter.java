package com.kirill.kochnev.homewardrope.mvp.presenters;

import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseMvpPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IAddUpdateThingView;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractThingRepository;
import com.kirill.kochnev.homewardrope.utils.ImageHelper;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.kirill.kochnev.homewardrope.utils.ImageHelper.makeImage;


@InjectViewState
public class AddUpdateThingPresenter extends BaseMvpPresenter<IAddUpdateThingView> {

    public static final String TAG = "AddUpdateThingPresenter";

    @Inject
    protected AbstractThingRepository things;

    private Thing model;

    public AddUpdateThingPresenter(long id) {
        WardropeApplication.getComponent().inject(this);
        if (id == AppConstants.DEFAULT_ID) {
            model = new Thing();
        } else {
            initValues(id);
        }
    }

    private void initValues(long id) {
        unsubscribeOnDestroy(things.getItem(id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(model -> {
                    this.model = model;
                    getViewState().updateView(model.getName(), model.getTag(), makeImage(model.getFullImagePath()));
                }, e -> Log.e(TAG, e.getMessage())));
    }

    public void saveThing(String name, String tag) {
        model.setName(name);
        model.setTag(tag);
        unsubscribeOnDestroy(things.putItem(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isPut -> getViewState().onSave()));
    }

    public void createUri() {
        File photoFile = null;
        try {
            photoFile = ImageHelper.createImageFile("thing");
            model.setFullImagePath(photoFile.getAbsolutePath());
            model.setIconImagePath(ImageHelper.createIconImageFile("thing").getAbsolutePath());
        } catch (IOException ex) {
            Log.e(TAG, "problems with creating image uri, error: " + ex.getMessage());
        }
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(WardropeApplication.getContext(), "com.kirill.kochnev.homewardrope", photoFile);
            getViewState().sendMakePhotoIntent(photoURI);
        }
    }

    public void processImage() {
        unsubscribeOnDestroy(ImageHelper.getAndSaveCropImageObservable(model.getFullImagePath(), model.getIconImagePath())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(img -> getViewState().setImage(img), ex -> getViewState().showError(ex.getMessage())));
    }


}
