package com.kirill.kochnev.homewardrope.mvp.presenters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.kirill.kochnev.homewardrope.AppConstants;
import com.kirill.kochnev.homewardrope.WardropeApplication;
import com.kirill.kochnev.homewardrope.db.models.Thing;
import com.kirill.kochnev.homewardrope.mvp.presenters.base.BaseMvpPresenter;
import com.kirill.kochnev.homewardrope.mvp.views.IAddUpdateThingView;
import com.kirill.kochnev.homewardrope.repositories.absclasses.AbstractThingRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.kirill.kochnev.homewardrope.AppConstants.COMPRESSION_PERCENT;
import static com.kirill.kochnev.homewardrope.AppConstants.REQ_HEIGHT;
import static com.kirill.kochnev.homewardrope.AppConstants.REQ_WIDTH;


@InjectViewState
public class AddUpdateThingPresenter extends BaseMvpPresenter<IAddUpdateThingView> {

    public static final String TAG = "AddUpdateThingPresenter";

    @Inject
    protected AbstractThingRepository things;

    private Thing model;
    private String imagePath;
    private String iconPath;


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
                    iconPath = model.getIconImagePath();
                    imagePath = model.getFullImagePath();
                    getViewState().updateView(model.getName(), model.getTag(), makeImage(imagePath));
                }, e -> e.printStackTrace()));
    }

    public void saveThing(String name, String tag) {
        model.setName(name);
        model.setTag(tag);
        model.setFullImagePath(imagePath);
        model.setIconImagePath(iconPath);
        unsubscribeOnDestroy(things.putItem(model)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isPut -> getViewState().onSave()));
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = WardropeApplication.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageUri = File.createTempFile(imageFileName, ".jpg", storageDir);
        File iconUri = File.createTempFile(imageFileName + "min_icon", ".jpg", storageDir);
        iconPath = iconUri.getAbsolutePath();
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

    private Bitmap makeImage(String imagePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        options.inSampleSize = calculateInSampleSize(options);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imagePath, options);
    }

    public void processImage() {
        Single<Bitmap> getCropImage = Single.create(sub -> {
            Bitmap cropImage = makeImage(imagePath);
            saveIcon(cropImage);
            if (cropImage != null) {
                sub.onSuccess(cropImage);
            } else {
                sub.onError(new Exception("can't get image"));
            }

        });
        unsubscribeOnDestroy(getCropImage.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(img -> getViewState().setImage(img), ex -> getViewState().showError(ex.getMessage())));
    }

    private void saveIcon(Bitmap cropImage) throws IOException {
        FileOutputStream out = new FileOutputStream(iconPath);
        cropImage.compress(Bitmap.CompressFormat.JPEG, COMPRESSION_PERCENT, out);
        out.flush();
        out.close();
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int height, int width) {
        int inSampleSize = 1;
        if (options.outHeight > height || options.outWidth > width) {
            int halfHeight = options.outHeight / 2;
            int halfWidth = options.outWidth / 2;
            while ((halfHeight / inSampleSize) >= REQ_HEIGHT
                    && (halfWidth / inSampleSize) >= REQ_WIDTH) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private int calculateInSampleSize(BitmapFactory.Options options) {
        return calculateInSampleSize(options, REQ_HEIGHT, REQ_WIDTH);
    }

}
