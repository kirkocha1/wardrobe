package com.kirill.kochnev.homewardrope.enums;

/**
 * Created by kirill on 05.04.17.
 */

public enum ViewMode {
    WARDROPE_MODE(1),
    THING_MODE(0),
    DEFAULT(-1);

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
