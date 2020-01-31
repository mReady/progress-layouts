/*
 * Copyright (C) 2017 mReady
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
 */

package net.mready.progresslayouts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import androidx.annotation.ArrayRes;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;

import net.mready.progresslayouts.internal.ProgressLayoutHelper;

public class ProgressScrollView extends ScrollView implements ProgressLayout {

    private final ProgressLayoutHelper progressLayoutHelper;
    private boolean loading = false;

    public ProgressScrollView(Context context) {
        this(context, null, 0);
    }

    public ProgressScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        progressLayoutHelper = new ProgressLayoutHelper(this, attrs);
    }

    @Override
    public void setLoading(boolean loading) {
        this.loading = loading;

        progressLayoutHelper.setLoading(loading);
        requestLayout();
        invalidate();
    }

    @Override
    public boolean isLoading() {
        return loading;
    }

    @Override
    public void setLoadingText(CharSequence text) {
        progressLayoutHelper.setLoadingText(text);
        requestLayout();
        invalidate();
    }

    @Override
    public void setLoadingText(@StringRes int textResId) {
        progressLayoutHelper.setLoadingText(textResId);
        requestLayout();
        invalidate();
    }

    @Override
    public void setLoadingTextAppearance(@StyleRes int resId) {
        progressLayoutHelper.setLoadingTextAppearance(resId);
        requestLayout();
        invalidate();
    }

    @Override
    public void setLoadingIndicatorColorRes(int colorResId) {
        progressLayoutHelper.setLoadingIndicatorColorRes(colorResId);
        requestLayout();
        invalidate();
    }

    @Override
    public void setLoadingIndicatorColor(@ColorInt int color) {
        progressLayoutHelper.setLoadingIndicatorColor(color);
        requestLayout();
        invalidate();
    }

    @Override
    public void setLoadingIndicatorColorResScheme(@ArrayRes int colorArrayResId) {
        progressLayoutHelper.setLoadingIndicatorColorResScheme(colorArrayResId);
        requestLayout();
        invalidate();
    }

    @Override
    public void setLoadingIndicatorColorResScheme(@NonNull int[] colorsResIds) {
        progressLayoutHelper.setLoadingIndicatorColorResScheme(colorsResIds);
        requestLayout();
        invalidate();
    }

    @Override
    public void setLoadingIndicatorColorScheme(@NonNull int[] colors) {
        progressLayoutHelper.setLoadingIndicatorColorScheme(colors);
        requestLayout();
        invalidate();
    }

    @Override
    public void setLoadingIndicatorDrawable(@DrawableRes int drawableResId) {
        progressLayoutHelper.setLoadingIndicatorDrawable(drawableResId);
        requestLayout();
        invalidate();
    }

    @Override
    public void setLoadingIndicatorDrawable(Drawable drawable) {
        progressLayoutHelper.setLoadingIndicatorDrawable(drawable);
        requestLayout();
        invalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        progressLayoutHelper.attach();
    }

    @Override
    protected void onDetachedFromWindow() {
        progressLayoutHelper.detach();
        super.onDetachedFromWindow();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return loading || super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (loading) {
            progressLayoutHelper.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (loading) {
            progressLayoutHelper.layout(changed, left, top, right, bottom);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (!loading) {
            super.dispatchDraw(canvas);
        } else {
            progressLayoutHelper.draw(canvas);
        }
    }

    @Override
    protected boolean verifyDrawable(@NonNull Drawable drawable) {
        return progressLayoutHelper.verifyDrawable(drawable) || super.verifyDrawable(drawable);
    }

}