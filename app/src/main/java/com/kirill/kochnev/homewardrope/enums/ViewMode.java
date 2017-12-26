package com.kirill.kochnev.homewardrope.enums;

import com.kirill.kochnev.homewardrope.AppConstants;

/**
 * Created by kirill on 05.04.17.
 */

public enum ViewMode {
    LOOK_MODE(2),
    WARDROBE_MODE(1),
    THING_MODE(0),
    DEFAULT(AppConstants.DEFAULT_ID);

    private int modeNum;

    ViewMode(int modeNum) {
        this.modeNum = modeNum;
    }

    public int getModeNum() {
        return modeNum;
    }

    public static ViewMode getByNum(int modeNum) {
        ViewMode result = DEFAULT;
        for (ViewMode mode : ViewMode.values()) {
            if (mode.getModeNum() == modeNum) {
                result = mode;
                break;
            }
        }
        return result;
    }
}
