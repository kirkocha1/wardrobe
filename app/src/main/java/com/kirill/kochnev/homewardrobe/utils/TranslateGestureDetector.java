package com.kirill.kochnev.homewardrobe.utils;

import android.support.v4.view.MotionEventCompat;
import android.util.Pair;
import android.view.MotionEvent;

import static android.view.MotionEvent.INVALID_POINTER_ID;

/**
 * Created by kirill on 10.05.17.
 */

public class TranslateGestureDetector {
    private int mActivePointerId;
    private float mLastTouchX = 0f;
    private float mLastTouchY = 0f;
    private float mPosX;
    private float mPosY;

    private TranslateGestureListener mListener;

    public TranslateGestureDetector(TranslateGestureListener mListener) {
        this.mListener = mListener;
    }

    public boolean onTouchEvent(MotionEvent event) {
        final int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                handleDownEvent(event);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                handleMoveEvent(event);
                break;
            }

            case MotionEvent.ACTION_UP: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                mActivePointerId = INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_POINTER_UP: {
                handlePointerUpEvent(event);
                break;
            }
        }
        return true;
    }

    private void handleMoveEvent(MotionEvent event) {
        final int pointerIndex = MotionEventCompat.findPointerIndex(event, mActivePointerId);
        final float x = MotionEventCompat.getX(event, pointerIndex);
        final float y = MotionEventCompat.getY(event, pointerIndex);
        final float dx = x - mLastTouchX;
        final float dy = y - mLastTouchY;

        mPosX += dx;
        mPosY += dy;
        mLastTouchX = x;
        mLastTouchY = y;
        if (mListener != null) {
            mListener.onTranslate(this);
        }
    }

    private void handleDownEvent(MotionEvent event) {
        final int pointerIndex = MotionEventCompat.getActionIndex(event);
        final float x = MotionEventCompat.getX(event, pointerIndex);
        final float y = MotionEventCompat.getY(event, pointerIndex);

        mLastTouchX = x;
        mLastTouchY = y;
        mActivePointerId = MotionEventCompat.getPointerId(event, 0);
    }

    private void handlePointerUpEvent(MotionEvent event) {
        final int pointerIndex = MotionEventCompat.getActionIndex(event);
        final int pointerId = MotionEventCompat.getPointerId(event, pointerIndex);

        if (pointerId == mActivePointerId) {
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mLastTouchX = MotionEventCompat.getX(event, newPointerIndex);
            mLastTouchY = MotionEventCompat.getY(event, newPointerIndex);
            mActivePointerId = MotionEventCompat.getPointerId(event, newPointerIndex);
        }
    }

    public Pair<Float, Float> getCoordinates() {
        return new Pair<>(mPosX, mPosY);
    }

    public interface TranslateGestureListener {

        void onTranslate(TranslateGestureDetector rotationDetector);
    }
}
