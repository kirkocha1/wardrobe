package com.kirill.kochnev.homewardrope.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.kirill.kochnev.homewardrope.WardropeApplication;

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


    public static Bitmap makeImage(String imagePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        options.inSampleSize = calculateInSampleSize(options);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imagePath, options);
    }

    private static void saveIcon(String iconPath, Bitmap cropImage) throws IOException {
        FileOutputStream out = new FileOutputStream(iconPath);
        cropImage.compress(Bitmap.CompressFormat.JPEG, COMPRESSION_PERCENT, out);
        out.flush();
        out.close();
    }

    public static Single<Bitmap> getAndSaveCropImageObservable(String fullImagePath, String iconPath) {
        return Single.create(sub -> {
            Bitmap cropImage = makeImage(fullImagePath);
            saveIcon(iconPath, cropImage);
            if (cropImage != null) {
                sub.onSuccess(cropImage);
            } else {
                sub.onError(new Exception("can't get image"));
            }
        });
    }

    public static Single<Bitmap> saveCropImageObservable(String iconPath, @NotNull Bitmap bitmap) {
        return Single.create(sub -> {
            try {
                saveIcon(iconPath, bitmap);
                sub.onSuccess(bitmap);
            } catch (Exception e) {
                sub.onError(new Exception("can't get image"));
            }
        });
    }

    public static File createImageFile(String name) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_" + name;
        File storageDir = WardropeApplication.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    public static File createIconImageFile(String name) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_" + name;
        File storageDir = WardropeApplication.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName + "min_icon", ".jpg", storageDir);
    }


    public static int calculateInSampleSize(BitmapFactory.Options options) {
        return calculateInSampleSize(options, REQ_HEIGHT, REQ_WIDTH);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int height, int width) {
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

}
