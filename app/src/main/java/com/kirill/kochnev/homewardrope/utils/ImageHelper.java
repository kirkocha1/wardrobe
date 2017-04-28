package com.kirill.kochnev.homewardrope.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileOutputStream;
import java.io.IOException;

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

    public static void saveIcon(String iconPath, Bitmap cropImage) throws IOException {
        FileOutputStream out = new FileOutputStream(iconPath);
        cropImage.compress(Bitmap.CompressFormat.JPEG, COMPRESSION_PERCENT, out);
        out.flush();
        out.close();
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
