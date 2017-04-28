package com.kirill.kochnev.homewardrope.mvp.presenters;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.Look;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseMvpPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IFirstStepCreationLookView;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractLookRepository;
import com.kirill.kochnev.homewardrope.utils.ImageHelper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
        } else {
            Toast.makeText(WardropeApplication.getContext(), "thing with id: " + id + " was added to look", Toast.LENGTH_SHORT).show();
        }

    }

    public void processLook(Bitmap bitmap) {
        try {
            createImageFile();
            ImageHelper.saveIcon(model.getFullImagePath(), bitmap);
            ImageHelper.saveIcon(model.getIconImagePath(), bitmap);
            Log.e(TAG, model.getIconImagePath());
            model.setName("test");
            looks.putItem(model).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(

            );
        } catch (Exception ex) {

        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = WardropeApplication.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageUri = File.createTempFile(imageFileName, ".jpg", storageDir);
        File iconUri = File.createTempFile(imageFileName + "min_icon", ".jpg", storageDir);
        model.setIconImagePath(iconUri.getAbsolutePath());
        model.setFullImagePath(imageUri.getAbsolutePath());
        return imageUri;
    }

    public void startCreationProcess() {
        getViewState().openCollageFragment(model.getThingIds());
    }

}
