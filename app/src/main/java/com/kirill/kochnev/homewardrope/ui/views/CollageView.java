package com.kirill.kochnev.homewardrope.ui.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.kirill.kochnev.homewardrope.enums.CollageMode;

/**
 * Created by kirill on 27.04.17.
 */

public class CollageView extends FrameLayout {

    public CollageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void inflateItems(@NonNull CollageMode mode, SparseArray<Bitmap> cache) {
        LayoutInflater.from(getContext()).inflate(mode.getLayout(), this, true);
        try {
            for (int i = 0; i < cache.size(); i++) {
                CollageItemView item = (CollageItemView) findViewById(mode.getViewIds()[i]);
                item.setImageBitmap(cache.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
