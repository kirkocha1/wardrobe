package com.kirill.kochnev.homewardrope.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import com.kirill.kochnev.homewardrope.utils.RotationGestureDetector;

import static android.view.MotionEvent.INVALID_POINTER_ID;

/**
 * Created by Kirill Kochnev on 11.04.17.
 */

public class CollageItemView extends ImageView {

    private ScaleGestureDetector mScaleDetector;
    private RotationGestureDetector mRotationDetector;


    private float mScaleFactor = 1.f;
    private float mRotationAngle = 0f;
    private float mLastTouchX = 0f;
    private float mLastTouchY = 0f;
    private float mPosX;
    private float mPosY;
    private int mActivePointerId;

    public CollageItemView(Context context) {
        super(context);
        initUi();
    }

    public CollageItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUi();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        mScaleDetector.onTouchEvent(event);
        mRotationDetector.onTouchEvent(event);
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
        invalidate();

        mLastTouchX = x;
        mLastTouchY = y;
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

    private void initUi() {
        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        mRotationDetector = new RotationGestureDetector(new RotationListener());
        setAdjustViewBounds(true);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);
        canvas.translate(mPosX, mPosY);
        canvas.rotate(mRotationAngle);
        super.onDraw(canvas);
        canvas.restore();
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            return true;
        }
    }

    private class RotationListener implements RotationGestureDetector.OnRotationGestureListener {
        @Override
        public void OnRotation(RotationGestureDetector rotationDetector) {
            mRotationAngle = -rotationDetector.getAngle();

        }
    }
}
