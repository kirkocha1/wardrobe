package com.kirill.kochnev.homewardrope.mvp.presenters;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.StringRes;
import android.support.v4.content.FileProvider;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.mvp.views.interfaces.IAddUpdateThingView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


@InjectViewState
public class AddUpdateThingPresenter extends MvpPresenter<IAddUpdateThingView> {
    String filePath;
    public void saveThing(String name) {

    }

    public void processCropImage(Bitmap pic) {
        getViewState().setImage(pic);
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = WardropeApplication.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        filePath = image.getAbsolutePath();
        return image;
    }

    public void createUri() {
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
        }
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(WardropeApplication.getContext(), "com.kirill.kochnev.homewardrope",
                    photoFile);
            getViewState().sendMakePhotoIntent(photoURI);
        }
    }
    public void processImage() {
        
    }

}
