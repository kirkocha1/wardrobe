package com.kirill.kochnev.homewardrobe.enums;

import android.support.annotation.LayoutRes;

import com.kirill.kochnev.homewardrobe.R;

/**
 * Created by kirill on 25.04.17.
 */

public enum CollageMode {

    TWO_IMAGE_MODE(2, R.layout.two_images_view, new int[]{R.id.two_image_first, R.id.two_image_second}),
    THREE_IMAGE_MODE(3, R.layout.three_images_view, new int[]{R.id.three_image_first, R.id.three_image_second, R.id.three_image_third}),
    FOUR_IMAGE_MODE(4, R.layout.four_images_view, new int[]{R.id.four_image_first, R.id.four_image_second, R.id.four_image_third, R.id.four_image_fours});

    private int layout;
    private int id;
    private int[] viewIds;

    CollageMode(int id, @LayoutRes int layout, int[] viewIds) {
        this.layout = layout;
        this.id = id;
        this.viewIds = viewIds;
    }

    public int getLayout() {
        return layout;
    }

    public int getId() {
        return id;
    }

    public int[] getViewIds() {
        return viewIds;
    }

    public static CollageMode getByNum(int modeNum) {
        CollageMode result = null;
        for (CollageMode mode : CollageMode.values()) {
            if (mode.getId() == modeNum) {
                result = mode;
                break;
            }
        }
        return result;
    }
}
