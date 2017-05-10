package com.kirill.kochnev.homewardrope.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import com.kirill.kochnev.homewardrope.utils.RotationGestureDetector;
import com.kirill.kochnev.homewardrope.utils.TranslateGestureDetector;

/**
 * Created by Kirill Kochnev on 11.04.17.
 */

public class CollageItemView extends ImageView {

    private ScaleGestureDetector mScaleDetector;
    private RotationGestureDetector mRotationDetector;
    private TranslateGestureDetector mTranslationDetector;

    private float mScaleFactor = 1.f;
    private float mRotationAngle = 0f;
    private float mPosX;
    private float mPosY;

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
        mTranslationDetector.onTouchEvent(event);
        return true;
    }

    private void initUi() {
        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        mRotationDetector = new RotationGestureDetector(rotationDetector -> {
            mRotationAngle = -rotationDetector.getAngle();
        });
        mTranslationDetector = new TranslateGestureDetector(translateDetector -> {
            mPosX = translateDetector.getCoordinates().first;
            mPosY = translateDetector.getCoordinates().second;
            invalidate();
        });
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);
        canvas.translate(mPosX, mPosY);
        Rect rect = getDrawable().getBounds();
        canvas.rotate(mRotationAngle, rect.exactCenterX(), rect.exactCenterY());
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

}
