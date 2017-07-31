/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * With some modifications made by mReady under Apache License, Version 2.0
 *
 */
package net.mready.progresslayouts.internal;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.DisplayMetrics;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class MaterialProgressDrawable extends Drawable implements Animatable {

    private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    private static final Interpolator MATERIAL_INTERPOLATOR = new FastOutSlowInInterpolator();
    private static final float FULL_ROTATION = 1080.0f;

    @Retention(RetentionPolicy.CLASS)
    @IntDef({LARGE, DEFAULT})
    public @interface ProgressDrawableSize {
    }

    // Maps to ProgressBar.Large style
    public static final int LARGE = 0;
    // Maps to ProgressBar default style
    public static final int DEFAULT = 1;
    // Maps to ProgressBar default style
    private static final int CIRCLE_DIAMETER = 40;
    private static final float CENTER_RADIUS = 8.75f; //should add up to 10 when + stroke_width
    private static final float STROKE_WIDTH = 2.5f;
    // Maps to ProgressBar.Large style
    private static final int CIRCLE_DIAMETER_LARGE = 56;
    private static final float CENTER_RADIUS_LARGE = 12.5f;
    private static final float STROKE_WIDTH_LARGE = 3f;
    private final int[] COLORS = new int[]{Color.WHITE};
    /**
     * The value in the linear interpolator for animating the drawable at which
     * the color transition should start
     */
    private static final float COLOR_START_DELAY_OFFSET = 0.75f;
    private static final float END_TRIM_START_DELAY_OFFSET = 0.5f;
    private static final float START_TRIM_DURATION_OFFSET = 0.5f;
    /**
     * The duration of a single progress spin in milliseconds.
     */
    private static final int ANIMATION_DURATION = 1332;
    /**
     * The number of points in the progress "star".
     */
    private static final float NUM_POINTS = 5f;
    /**
     * The indicator ring, used to manage animation state.
     */
    private final Ring mRing;
    /**
     * Canvas rotation in degrees.
     */
    private float mRotation;
    private static final float MAX_PROGRESS_ARC = .8f;
    private Resources mResources;
    private Animator mAnimator;
    private float mRotationCount;
    private double mWidth;
    private double mHeight;
    private boolean mFinishing;

    public MaterialProgressDrawable(Context context) {
        mResources = context.getResources();
        mRing = new Ring(mCallback);
        mRing.setColors(COLORS);
        updateSizes(DEFAULT);
        setupAnimator();
    }

    private void setSizeParameters(double progressCircleWidth, double progressCircleHeight,
                                   double centerRadius, double strokeWidth) {
        final Ring ring = mRing;
        final DisplayMetrics metrics = mResources.getDisplayMetrics();
        final float screenDensity = metrics.density;
        mWidth = progressCircleWidth * screenDensity;
        mHeight = progressCircleHeight * screenDensity;
        ring.setStrokeWidth((float) strokeWidth * screenDensity);
        ring.setCenterRadius(centerRadius * screenDensity);
        ring.setColorIndex(0);
    }

    /**
     * Set the overall size for the progress spinner. This updates the radius
     * and stroke width of the ring.
     */
    public void updateSizes(@ProgressDrawableSize int size) {
        if (size == LARGE) {
            setSizeParameters(CIRCLE_DIAMETER_LARGE, CIRCLE_DIAMETER_LARGE, CENTER_RADIUS_LARGE,
                    STROKE_WIDTH_LARGE);
        } else {
            setSizeParameters(CIRCLE_DIAMETER, CIRCLE_DIAMETER, CENTER_RADIUS, STROKE_WIDTH);
        }
    }

    /**
     * Set the start and end trim for the progress spinner arc.
     *
     * @param startAngle start angle
     * @param endAngle   end angle
     */
    public void setStartEndTrim(float startAngle, float endAngle) {
        mRing.setStartTrim(startAngle);
        mRing.setEndTrim(endAngle);
    }

    /**
     * Set the amount of rotation to apply to the progress spinner.
     *
     * @param rotation Rotation is from [0..1]
     */
    public void setProgressRotation(float rotation) {
        mRing.setRotation(rotation);
    }

    /**
     * Update the background color of the circle image view.
     */
    public void setBackgroundColor(int color) {
        mRing.setBackgroundColor(color);
    }

    /**
     * Set the colors used in the progress animation from color resources.
     * The first color will also be the color of the bar that grows in response
     * to a user swipe gesture.
     *
     * @param colors
     */
    public void setColorSchemeColors(int... colors) {
        mRing.setColors(colors);
        mRing.setColorIndex(0);
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) mHeight;
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) mWidth;
    }

    @Override
    public void draw(Canvas c) {
        final Rect bounds = getBounds();
        final int saveCount = c.save();
        c.rotate(mRotation, bounds.exactCenterX(), bounds.exactCenterY());
        mRing.draw(c, bounds);
        c.restoreToCount(saveCount);
    }

    @Override
    public void setAlpha(int alpha) {
        mRing.setAlpha(alpha);
    }

    public int getAlpha() {
        return mRing.getAlpha();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mRing.setColorFilter(colorFilter);
    }

    void setRotation(float rotation) {
        mRotation = rotation;
        invalidateSelf();
    }

    private float getRotation() {
        return mRotation;
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public boolean isRunning() {
        return mAnimator.isRunning();
    }

    @Override
    public void start() {
        mAnimator.cancel();
        mRing.storeOriginals();
        // Already showing some part of the ring
        if (mRing.getEndTrim() != mRing.getStartTrim()) {
            mFinishing = true;
            mAnimator.setDuration(ANIMATION_DURATION / 2);
            mAnimator.start();
        } else {
            mRing.setColorIndex(0);
            mRing.resetOriginals();
            mAnimator.setDuration(ANIMATION_DURATION);
            mAnimator.start();
        }
    }

    @Override
    public void stop() {
        mAnimator.cancel();
        setRotation(0);
        mRing.setColorIndex(0);
        mRing.resetOriginals();
    }

    private float getMinProgressArc(Ring ring) {
        return (float) Math.toRadians(
                ring.getStrokeWidth() / (2 * Math.PI * ring.getCenterRadius()));
    }

    // Adapted from ArgbEvaluator.java
    private int evaluateColorChange(float fraction, int startValue, int endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;
        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;
        return (int) ((startA + (int) (fraction * (endA - startA))) << 24) |
                (int) ((startR + (int) (fraction * (endR - startR))) << 16) |
                (int) ((startG + (int) (fraction * (endG - startG))) << 8) |
                (int) ((startB + (int) (fraction * (endB - startB))));
    }

    /**
     * Update the ring color if this is within the last 25% of the animation.
     * The new ring color will be a translation from the starting ring color to
     * the next color.
     */
    private void updateRingColor(float interpolatedTime, Ring ring) {
        if (interpolatedTime > COLOR_START_DELAY_OFFSET) {
            // scale the interpolatedTime so that the full
            // transformation from 0 - 1 takes place in the
            // remaining time
            ring.setColor(evaluateColorChange((interpolatedTime - COLOR_START_DELAY_OFFSET)
                            / (1.0f - COLOR_START_DELAY_OFFSET), ring.getStartingColor(),
                    ring.getNextColor()));
        }
    }

    private void applyFinishTranslation(float interpolatedTime, Ring ring) {
        // shrink back down and complete a full rotation before
        // starting other circles
        // Rotation goes between [0..1].
        updateRingColor(interpolatedTime, ring);
        float targetRotation = (float) (Math.floor(ring.getStartingRotation() / MAX_PROGRESS_ARC)
                + 1f);
        final float minProgressArc = getMinProgressArc(ring);
        final float startTrim = ring.getStartingStartTrim()
                + (ring.getStartingEndTrim() - minProgressArc - ring.getStartingStartTrim())
                * interpolatedTime;
        ring.setStartTrim(startTrim);
        ring.setEndTrim(ring.getStartingEndTrim());
        final float rotation = ring.getStartingRotation()
                + ((targetRotation - ring.getStartingRotation()) * interpolatedTime);
        ring.setRotation(rotation);
    }

    private void setupAnimator() {
        final Ring ring = mRing;

        ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 1.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setInterpolator(LINEAR_INTERPOLATOR);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float interpolatedTime = (float) animation.getAnimatedValue();
                if (mFinishing) {
                    applyFinishTranslation(interpolatedTime, ring);
                } else {
                    // The minProgressArc is calculated from 0 to create an
                    // angle that matches the stroke width.
                    final float minProgressArc = getMinProgressArc(ring);
                    final float startingEndTrim = ring.getStartingEndTrim();
                    final float startingTrim = ring.getStartingStartTrim();
                    final float startingRotation = ring.getStartingRotation();
                    updateRingColor(interpolatedTime, ring);
                    // Moving the start trim only occurs in the first 50% of a
                    // single ring animation
                    if (interpolatedTime <= START_TRIM_DURATION_OFFSET) {
                        // scale the interpolatedTime so that the full
                        // transformation from 0 - 1 takes place in the
                        // remaining time
                        final float scaledTime = (interpolatedTime)
                                / (1.0f - START_TRIM_DURATION_OFFSET);
                        final float startTrim = startingTrim
                                + ((MAX_PROGRESS_ARC - minProgressArc) * MATERIAL_INTERPOLATOR
                                .getInterpolation(scaledTime));
                        ring.setStartTrim(startTrim);
                    }
                    // Moving the end trim starts after 50% of a single ring
                    // animation completes
                    if (interpolatedTime > END_TRIM_START_DELAY_OFFSET) {
                        // scale the interpolatedTime so that the full
                        // transformation from 0 - 1 takes place in the
                        // remaining time
                        final float minArc = MAX_PROGRESS_ARC - minProgressArc;
                        float scaledTime = (interpolatedTime - START_TRIM_DURATION_OFFSET)
                                / (1.0f - START_TRIM_DURATION_OFFSET);
                        final float endTrim = startingEndTrim
                                + (minArc * MATERIAL_INTERPOLATOR.getInterpolation(scaledTime));
                        ring.setEndTrim(endTrim);
                    }
                    final float rotation = startingRotation + (0.25f * interpolatedTime);
                    ring.setRotation(rotation);
                    float groupRotation = ((FULL_ROTATION / NUM_POINTS) * interpolatedTime)
                            + (FULL_ROTATION * (mRotationCount / NUM_POINTS));
                    setRotation(groupRotation);
                }
            }
        });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                mRotationCount = 0;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // do nothing
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // do nothing
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                ring.storeOriginals();
                ring.goToNextColor();
                ring.setStartTrim(ring.getEndTrim());
                if (mFinishing) {
                    // finished closing the last ring from the swipe gesture; go
                    // into progress mode
                    mFinishing = false;
                    animation.setDuration(ANIMATION_DURATION);
                } else {
                    mRotationCount = (mRotationCount + 1) % (NUM_POINTS);
                }
            }
        });

        mAnimator = animator;
    }

    private final Callback mCallback = new Callback() {
        @Override
        public void invalidateDrawable(Drawable d) {
            invalidateSelf();
        }

        @Override
        public void scheduleDrawable(Drawable d, Runnable what, long when) {
            scheduleSelf(what, when);
        }

        @Override
        public void unscheduleDrawable(Drawable d, Runnable what) {
            unscheduleSelf(what);
        }
    };

    private static class Ring {
        private final RectF mTempBounds = new RectF();
        private final Paint mPaint = new Paint();
        private final Paint mArrowPaint = new Paint();
        private final Callback mCallback;
        private float mStartTrim = 0.0f;
        private float mEndTrim = 0.0f;
        private float mRotation = 0.0f;
        private float mStrokeWidth = 5.0f;
        private float mStrokeInset = 2.5f;
        private int[] mColors;
        // mColorIndex represents the offset into the available mColors that the
        // progress circle should currently display. As the progress circle is
        // animating, the mColorIndex moves by one to the next available color.
        private int mColorIndex;
        private float mStartingStartTrim;
        private float mStartingEndTrim;
        private float mStartingRotation;
        private double mRingCenterRadius;
        private int mAlpha = 0;
        private final Paint mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private int mBackgroundColor = Color.TRANSPARENT;
        private int mCurrentColor;

        Ring(Callback callback) {
            mCallback = callback;
            mPaint.setStrokeCap(Paint.Cap.SQUARE);
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Style.STROKE);
            mArrowPaint.setStyle(Style.FILL);
            mArrowPaint.setAntiAlias(true);
        }

        void setBackgroundColor(int color) {
            mBackgroundColor = color;
        }

        /**
         * Draw the progress spinner
         */
        void draw(Canvas c, Rect bounds) {
            final RectF arcBounds = mTempBounds;
            arcBounds.set(bounds);
            arcBounds.inset(mStrokeInset, mStrokeInset);
            final float startAngle = (mStartTrim + mRotation) * 360;
            final float endAngle = (mEndTrim + mRotation) * 360;
            float sweepAngle = endAngle - startAngle;

            mCirclePaint.setColor(mBackgroundColor);
            mCirclePaint.setAlpha(mAlpha);
            c.drawCircle(bounds.exactCenterX(), bounds.exactCenterY(), arcBounds.width() / 2,
                    mCirclePaint);

            mPaint.setColor(mCurrentColor);
            c.drawArc(arcBounds, startAngle, sweepAngle, false, mPaint);
        }

        /**
         * Set the colors the progress spinner alternates between.
         *
         * @param colors Array of integers describing the colors. Must be non-<code>null</code>.
         */
        void setColors(@NonNull int[] colors) {
            mColors = colors;
            // if colors are reset, make sure to reset the color index as well
            setColorIndex(0);
        }

        /**
         * Set the absolute color of the progress spinner. This is should only
         * be used when animating between current and next color when the
         * spinner is rotating.
         *
         * @param color int describing the color.
         */
        public void setColor(int color) {
            mCurrentColor = color;
        }

        /**
         * @param index Index into the color array of the color to display in
         *              the progress spinner.
         */
        void setColorIndex(int index) {
            mColorIndex = index;
            mCurrentColor = mColors[mColorIndex];
        }

        /**
         * @return int describing the next color the progress spinner should use when drawing.
         */
        int getNextColor() {
            return mColors[getNextColorIndex()];
        }

        private int getNextColorIndex() {
            return (mColorIndex + 1) % (mColors.length);
        }

        /**
         * Proceed to the next available ring color. This will automatically
         * wrap back to the beginning of colors.
         */
        void goToNextColor() {
            setColorIndex(getNextColorIndex());
        }

        void setColorFilter(ColorFilter filter) {
            mPaint.setColorFilter(filter);
            invalidateSelf();
        }

        /**
         * @param alpha Set the alpha of the progress spinner and associated arrowhead.
         */
        public void setAlpha(int alpha) {
            mAlpha = alpha;
        }

        /**
         * @return Current alpha of the progress spinner and arrowhead.
         */
        public int getAlpha() {
            return mAlpha;
        }

        /**
         * @param strokeWidth Set the stroke width of the progress spinner in pixels.
         */
        void setStrokeWidth(float strokeWidth) {
            mStrokeWidth = strokeWidth;
            mPaint.setStrokeWidth(strokeWidth);
            mStrokeInset = (float) Math.ceil(mStrokeWidth / 2.0f);
            invalidateSelf();
        }

        float getStrokeWidth() {
            return mStrokeWidth;
        }

        void setStartTrim(float startTrim) {
            mStartTrim = startTrim;
            invalidateSelf();
        }

        float getStartTrim() {
            return mStartTrim;
        }

        float getStartingStartTrim() {
            return mStartingStartTrim;
        }

        float getStartingEndTrim() {
            return mStartingEndTrim;
        }

        int getStartingColor() {
            return mColors[mColorIndex];
        }

        void setEndTrim(float endTrim) {
            mEndTrim = endTrim;
            invalidateSelf();
        }

        float getEndTrim() {
            return mEndTrim;
        }

        void setRotation(float rotation) {
            mRotation = rotation;
            invalidateSelf();
        }

        /**
         * @param centerRadius Inner radius in px of the circle the progress
         *                     spinner arc traces.
         */
        void setCenterRadius(double centerRadius) {
            mRingCenterRadius = centerRadius;
        }

        double getCenterRadius() {
            return mRingCenterRadius;
        }

        /**
         * @return The amount the progress spinner is currently rotated, between [0..1].
         */
        float getStartingRotation() {
            return mStartingRotation;
        }

        /**
         * If the start / end trim are offset to begin with, store them so that
         * animation starts from that offset.
         */
        void storeOriginals() {
            mStartingStartTrim = mStartTrim;
            mStartingEndTrim = mEndTrim;
            mStartingRotation = mRotation;
        }

        /**
         * Reset the progress spinner to default rotation, start and end angles.
         */
        void resetOriginals() {
            mStartingStartTrim = 0;
            mStartingEndTrim = 0;
            mStartingRotation = 0;
            setStartTrim(0);
            setEndTrim(0);
            setRotation(0);
        }

        private void invalidateSelf() {
            mCallback.invalidateDrawable(null);
        }
    }

}