package com.kirill.kochnev.homewardrope.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.kirill.kochnev.homewardrope.AppConstants;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Single;

import static com.kirill.kochnev.homewardrope.AppConstants.COMPRESSION_PERCENT;
import static com.kirill.kochnev.homewardrope.AppConstants.REQ_HEIGHT;
import static com.kirill.kochnev.homewardrope.AppConstants.REQ_WIDTH;

/**
 * Created by kirill on 28.04.17.
 */

public class ImageHelper {

    private Context context;

    public ImageHelper(Context context) {
        this.context = context;
    }

    private void saveIcon(String iconPath, Bitmap cropImage, boolean withCompession) throws IOException {
        FileOutputStream out = new FileOutputStream(iconPath);
        cropImage.compress(Bitmap.CompressFormat.JPEG, withCompession ? COMPRESSION_PERCENT : AppConstants.ALL_PERCENTS, out);
        out.flush();
        out.close();
    }

    public Bitmap makeImage(String imagePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        options.inSampleSize = calculateInSampleSize(options);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imagePath, options);
    }

    public Single<Bitmap> getAndSaveCropImageObservable(String fullImagePath, String iconPath) {
        return Single.fromCallable(() -> {
            Bitmap cropImage = makeImage(fullImagePath);
            saveIcon(iconPath, cropImage, true);
            return cropImage;
        });
    }

    public Single<Bitmap> saveImageAndIconObservable(String imagePath, String iconPath, @NotNull Bitmap bitmap) {
        return Single.fromCallable(() -> {
            saveIcon(imagePath, bitmap, false);
            saveIcon(iconPath, bitmap, true);
            return bitmap;
        });
    }

    public File createImageFile(String name) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_" + name;
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    public File createIconImageFile(String name) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_" + name;
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName + "min_icon", ".jpg", storageDir);
    }

    public int calculateInSampleSize(BitmapFactory.Options options) {
        return calculateInSampleSize(options, REQ_HEIGHT, REQ_WIDTH);
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int height, int width) {
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

    public void deleteImage(String... paths) {
        for (String path : paths) {
            new File(path).delete();
        }
    }
}
