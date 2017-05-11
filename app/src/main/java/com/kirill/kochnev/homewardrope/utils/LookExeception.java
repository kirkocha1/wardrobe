package com.kirill.kochnev.homewardrope.utils;

/**
 * Created by kirill on 11.05.17.
 */

public class LookExeception extends Exception {
    private boolean isNotEnough;

    public LookExeception(String message, boolean isNotEnough) {
        super(message);
        this.isNotEnough = isNotEnough;
    }

    public boolean isNotEnough() {
        return isNotEnough;
    }
}
