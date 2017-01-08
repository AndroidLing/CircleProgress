package com.doctor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import doctor.com.R;

/**
 * desc:
 * created by hjl on 2017/1/3 8:04
 */

public class CircleProgress extends View implements CircleProgressAttrs {

    private final static String TAG = CircleProgress.class.getSimpleName();

    private int[] mColorSchemeColors = new int[1];
    private float mCircleWidth;

    private Paint mPaint;
    private RectF mRectF;
    private int mColorsIndex = 0;
    private boolean isShow;

    private float mOffsetDegree = 0;
    private float mArcDegree = 15;

    private final int mUniformAnimationDuration = 1500;
    private final int mEnlargeAnimationDuration = 575;
    private final int mShrinkAnimationDuration = 575;
    private final int mAnimationDelayDuration = 175;

    private float mLastUniformValue;
    private float mLastEnlargeValue;
    private float mLastShrinkValue;

    private ValueAnimator mUniformAnimator;
    private ValueAnimator mEnlargeAnimator;
    private ValueAnimator mShrinkAnimator;

    public CircleProgress(Context context) {
        this(context, null);
    }

    public CircleProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress, defStyleAttr, 0);
        Resources resources = getResources();
        mCircleWidth = typedArray.getDimension(R.styleable.CircleProgress_circleWidth, resources.getDimension(R.dimen.CircleWidth));
        mColorSchemeColors[0] = typedArray.getColor(R.styleable.CircleProgress_circleColor, resources.getColor(R.color.circle));
        typedArray.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(mCircleWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mColorSchemeColors[mColorsIndex]);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createRectF();
    }

    private void createRectF() {
        /**
         * Calculate the size and position of the rectangle,
         * so that the length and width of the rectangle is always equal
         */
        float size = Math.min(getWidth() - getPaddingLeft() - getPaddingRight(), getHeight() - getPaddingTop() - getPaddingBottom());
        float left = 0f + mCircleWidth + getPaddingLeft();
        float top = 0f + mCircleWidth + getPaddingTop();
        float right = size - mCircleWidth + getPaddingLeft();
        float bottom = size - mCircleWidth + getPaddingTop();

        /**
         * Generate rectangle
         */
        mRectF = new RectF(left, top, right, bottom);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mRectF, mOffsetDegree, mArcDegree, false, mPaint);
        if (mOffsetDegree > 360) mOffsetDegree %= 360;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (getVisibility() == View.VISIBLE) startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        abortAnimation();
    }

    private void reset() {
        mArcDegree = 15;
        mColorsIndex = 0;
    }

    @Override
    public void setCircleWidth(float width) {
        this.mCircleWidth = width;
        mPaint.setStrokeWidth(width);
        createRectF();
    }

    @Override
    public float getCircleWidth() {
        return mCircleWidth;
    }

    @Override
    public void setSchemeColors(int... colors) {
        this.mColorSchemeColors = colors;
        mColorsIndex = -1;
    }

    @Override
    public int[] getSchemeColors() {
        return mColorSchemeColors;
    }

    public void show() {
        if (isShow) return;
        setVisibility(VISIBLE);
        startAnimation();
    }

    public void hide() {
        if (!isShow) return;
        setVisibility(INVISIBLE);
        abortAnimation();
        reset();
    }


    private void startAnimation() {
        isShow = true;
        startUniformAnimation();
        startEnlargeAnimaion();
    }

    private void abortAnimation() {
        isShow = false;
        if (mUniformAnimator != null && mUniformAnimator.isStarted()) {
            mUniformAnimator.cancel();
            mUniformAnimator = null;
        }
        if (mEnlargeAnimator != null && mEnlargeAnimator.isStarted()) {
            mEnlargeAnimator.cancel();
            mEnlargeAnimator = null;
        }
        if (mShrinkAnimator != null && mShrinkAnimator.isStarted()) {
            mShrinkAnimator.cancel();
            mShrinkAnimator = null;
        }
    }

    private void startUniformAnimation() {
        if (mUniformAnimator == null) {
            mUniformAnimator = ValueAnimator.ofFloat(0, 360);
            mUniformAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float value = (float) valueAnimator.getAnimatedValue();
                    float offsetValue = value - mLastUniformValue;
                    mOffsetDegree += offsetValue;
                    mLastUniformValue = value;
                    invalidate();
                }
            });
            mUniformAnimator.setInterpolator(new LinearInterpolator());
            mUniformAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mUniformAnimator.setDuration(mUniformAnimationDuration);
        }
        if (mUniformAnimator.isStarted() || mUniformAnimator.isRunning()) {
            return;
        }
        mLastUniformValue = 0;
        mUniformAnimator.start();
    }


    private void startEnlargeAnimaion() {
        if (mEnlargeAnimator == null) {
            mEnlargeAnimator = ValueAnimator.ofFloat(0, 240);
            mEnlargeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float value = (float) valueAnimator.getAnimatedValue();
                    float offsetValue = value - mLastEnlargeValue;
                    mArcDegree += offsetValue;
                    mLastEnlargeValue = value;
                }
            });
            mEnlargeAnimator.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    mPaint.setColor(mColorSchemeColors[mColorsIndex = ++mColorsIndex % mColorSchemeColors.length]);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (isShow) startShrinkAnimation();
                }
            });
            mEnlargeAnimator.setInterpolator(new DecelerateInterpolator());
            mEnlargeAnimator.setDuration(mEnlargeAnimationDuration);
            mEnlargeAnimator.setStartDelay(mAnimationDelayDuration);
        }
        if (mEnlargeAnimator.isStarted() || mEnlargeAnimator.isRunning()) {
            return;
        }
        mLastEnlargeValue = 0;
        mEnlargeAnimator.start();
    }


    private void startShrinkAnimation() {
        if (mShrinkAnimator == null) {
            mShrinkAnimator = ValueAnimator.ofFloat(0, 240);
            mShrinkAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float value = (float) valueAnimator.getAnimatedValue();
                    float offsetValue = value - mLastShrinkValue;
                    mArcDegree -= offsetValue;
                    mOffsetDegree += offsetValue;
                    mLastShrinkValue = value;
                }
            });
            mShrinkAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (isShow) startEnlargeAnimaion();
                }
            });
            mShrinkAnimator.setInterpolator(new DecelerateInterpolator());
            mShrinkAnimator.setDuration(mShrinkAnimationDuration);
            mShrinkAnimator.setStartDelay(mAnimationDelayDuration);
        }
        if (mShrinkAnimator.isStarted() || mShrinkAnimator.isRunning()) {
            return;
        }
        mLastShrinkValue = 0;
        mShrinkAnimator.start();
    }
}
