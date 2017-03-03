package com.kirill.kochnev.homewardrope.mvp.presenters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.db.models.ThingDao;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IAddUpdateThingView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;


@InjectViewState
public class AddUpdateThingPresenter extends MvpPresenter<IAddUpdateThingView> {

    public static final String TAG = "AddUpdateThingPresenter";
    private static final int REQ_HEIGHT = 640;
    private static final int REQ_WIDTH = 480;

    @Inject
    protected ThingDao dao;
    private Thing model;
    private String imagePath;

    public AddUpdateThingPresenter(long id) {
        WardropeApplication.getComponent().inject(this);
        if (id == -1) {
            model = new Thing();
        }
    }

    public void saveThing(String name, String tag) {
        model.setName(name);
        model.setTag(tag);
        model.setFilePath(imagePath);
        dao.insert(model);
        getViewState().onSave();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = WardropeApplication.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageUri = File.createTempFile(imageFileName, ".jpg", storageDir);
        imagePath = imageUri.getAbsolutePath();
        return imageUri;
    }

    public void createUri() {
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            Log.e(TAG, "problems with creating image uri, error: " + ex.getMessage());
        }
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(WardropeApplication.getContext(), "com.kirill.kochnev.homewardrope",
                    photoFile);
            getViewState().sendMakePhotoIntent(photoURI);
        }
    }

    public void processImage() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        options.inSampleSize = calculateInSampleSize(options);
        options.inJustDecodeBounds = false;
        Bitmap cropImage = BitmapFactory.decodeFile(imagePath, options);
        getViewState().setImage(cropImage);
    }

    private int calculateInSampleSize(BitmapFactory.Options options) {
        int inSampleSize = 1;
        if (options.outHeight > REQ_HEIGHT || options.outWidth > REQ_WIDTH) {
            int halfHeight = options.outHeight / 2;
            int halfWidth = options.outWidth / 2;

            while ((halfHeight / inSampleSize) >= REQ_HEIGHT
                    && (halfWidth / inSampleSize) >= REQ_WIDTH) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

}
