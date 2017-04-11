package com.kirill.kochnev.homewardrope.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.kirill.kochnev.homewardrope.R;

/**
 * Created by Kirill Kochnev on 11.04.17.
 */

public class TestView extends ImageView {

    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;

    private int left, top;
    private int parentLeft, parentTop;


    public TestView(Context context) {
        super(context);
        initUi();
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUi();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        mScaleDetector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                parentLeft = ((ViewGroup) getParent()).getLeft();
                parentTop = ((ViewGroup) getParent()).getTop();
                left = (int) event.getRawX() - getLeft() - parentLeft;
                top = (int) event.getRawY() - getTop() - parentTop;
                break;
            case MotionEvent.ACTION_MOVE:
                int leftMargin = (int) (event.getRawX() - left - parentLeft);
                int topMargin = (int) (event.getRawY() - top - parentTop);

                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) getLayoutParams();
                if (leftMargin >= 0 && leftMargin + getWidth() <= ((ViewGroup) getParent()).getWidth())
                    params.leftMargin = leftMargin;
                if (topMargin >= 0 && topMargin + getHeight() <= ((ViewGroup) getParent()).getHeight())
                    params.topMargin = topMargin;

                params.gravity = Gravity.NO_GRAVITY;
                setLayoutParams(params);
                break;
        }
        return true;
    }

    private void initUi() {
        setImageDrawable(getResources().getDrawable(R.drawable.close));
        mScaleDetector = new ScaleGestureDetector(getContext(), new ScaleListener());
        setTag("TEST");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);
        getDrawable().draw(canvas);
        canvas.restore();
    }


    public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            requestLayout();
            invalidate();
            return true;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec *= mScaleFactor;
        heightMeasureSpec *= mScaleFactor;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
