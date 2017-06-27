package com.kirill.kochnev.homewardrope.db.models;

import android.graphics.Bitmap;

/**
 * Created by Kirill Kochnev on 27.06.17.
 */

public abstract class BitmapModel {

    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
