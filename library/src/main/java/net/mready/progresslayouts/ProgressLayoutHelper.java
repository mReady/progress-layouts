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
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;

import androidx.annotation.ArrayRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.facebook.fbui.textlayoutbuilder.ResourceTextLayoutHelper;
import com.facebook.fbui.textlayoutbuilder.TextLayoutBuilder;

import net.mready.progresslayouts.internal.MaterialProgressDrawable;

public class ProgressLayoutHelper {

    private static final int DRAWABLE_MAX_LEVEL = 10000;
    private static final int DRAWABLE_LEVEL_INCREMENT = 50;

    private final ViewGroup viewGroup;

    private Drawable drawable;
    private Layout textLayout;
    private int textAppearance;
    private boolean loading;

    private int scrollX;
    private int scrollY;

    public ProgressLayoutHelper(final ViewGroup viewGroup, AttributeSet attrs) {
        this.viewGroup = viewGroup;

        Context context = viewGroup.getContext();
        Resources resources = context.getResources();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressLayout,
                R.attr.progressLayoutStyle, 0);

        if (!viewGroup.isInEditMode()) {
            drawable = a.getDrawable(R.styleable.ProgressLayout_loadingIndicatorDrawable);

            if (drawable == null) {
                drawable = new MaterialProgressDrawable(context);

                int colorListRes = a.getResourceId(R.styleable.ProgressLayout_loadingIndicatorColorScheme, -1);
                if (colorListRes != -1) {
                    int[] colorList = resources.getIntArray(colorListRes);
                    ((MaterialProgressDrawable) drawable).setColorSchemeColors(colorList);
                } else {
                    int defaultColor = ContextCompat.getColor(context, R.color.default_loading_indicator_color);
                    TypedValue value = new TypedValue();
                    if (context.getTheme().resolveAttribute(R.attr.colorAccent, value, true)) {
                        defaultColor = value.data;
                    }

                    int color = a.getColor(R.styleable.ProgressLayout_loadingIndicatorColor, defaultColor);
                    ((MaterialProgressDrawable) drawable).setColorSchemeColors(color);
                }
            }

            TextLayoutBuilder builder = new TextLayoutBuilder()
                    .setTextSize(resources.getDimensionPixelSize(R.dimen.default_loading_text_size))
                    .setText(a.getText(R.styleable.ProgressLayout_loadingText));

            textAppearance = a.getResourceId(R.styleable.ProgressLayout_loadingTextAppearance, -1);
            if (textAppearance != -1) {
                ResourceTextLayoutHelper.setTextAppearance(builder, context, textAppearance);
            }

            textLayout = builder.build();
        }

        a.recycle();
    }

    public void attach() {
        if (loading) {
            startAnimatable();
        } else {
            stopAnimatable();
        }
    }

    public void detach() {
        stopAnimatable();
    }

    public void setLoading(boolean loading) {
        if (this.loading == loading) {
            return;
        }

        this.loading = loading;

        if (loading) {
            startAnimatable();
        } else {
            stopAnimatable();
        }

        ViewCompat.postInvalidateOnAnimation(viewGroup);
    }

    private void startAnimatable() {
        if (drawable instanceof Animatable) {
            Animatable animatable = (Animatable) drawable;
            if (!animatable.isRunning()) {
                drawable.setCallback(viewGroup);
                animatable.start();
            }
        }
    }

    private void stopAnimatable() {
        if (drawable instanceof Animatable) {
            Animatable animatable = (Animatable) drawable;
            if (animatable.isRunning()) {
                animatable.stop();
                drawable.setCallback(null);
            }
        }
    }

    public void setLoadingText(CharSequence text) {
        TextLayoutBuilder builder = new TextLayoutBuilder().setText(text);
        if (textAppearance != -1) {
            ResourceTextLayoutHelper.setTextAppearance(builder, viewGroup.getContext(), textAppearance);
        }
        textLayout = builder.build();
    }

    public void setLoadingText(@StringRes int textResId) {
        setLoadingText(viewGroup.getResources().getString(textResId));
    }

    public void setLoadingTextAppearance(@StyleRes int textAppearance) {
        this.textAppearance = textAppearance;

        String message = "";
        if (textLayout != null) {
            message = textLayout.getText().toString();
        }

        setLoadingText(message);
    }

    public void setLoadingIndicatorColor(@ColorInt int color) {
        if (drawable instanceof MaterialProgressDrawable) {
            ((MaterialProgressDrawable) drawable).setColorSchemeColors(color);
        }
    }

    public void setLoadingIndicatorColorRes(@ColorRes int colorResId) {
        if (drawable instanceof MaterialProgressDrawable) {
            ((MaterialProgressDrawable) drawable)
                    .setColorSchemeColors(ContextCompat.getColor(viewGroup.getContext(), colorResId));
        }
    }

    public void setLoadingIndicatorColorResScheme(@ArrayRes int colorArrayResId) {
        if (drawable instanceof MaterialProgressDrawable) {
            int[] colors = viewGroup.getResources().getIntArray(colorArrayResId);
            ((MaterialProgressDrawable) drawable).setColorSchemeColors(colors);
        }
    }

    public void setLoadingIndicatorColorScheme(@NonNull int[] colors) {
        if (drawable instanceof MaterialProgressDrawable) {
            ((MaterialProgressDrawable) drawable).setColorSchemeColors(colors);
        }
    }

    public void setLoadingIndicatorColorResScheme(@NonNull int[] colorsResIds) {
        if (drawable instanceof MaterialProgressDrawable) {
            int[] colors = new int[colorsResIds.length];
            for (int i = 0; i < colorsResIds.length; i++) {
                colors[i] = ContextCompat.getColor(viewGroup.getContext(), colorsResIds[i]);
            }
            ((MaterialProgressDrawable) drawable).setColorSchemeColors(colors);
        }
    }

    public void setLoadingIndicatorDrawable(@DrawableRes int drawableResId) {
        drawable = ContextCompat.getDrawable(viewGroup.getContext(), drawableResId);
    }

    public void setLoadingIndicatorDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public void measure(int widthMeasureSpec, int heightMeasureSpec) {
        // Nothing right now
    }

    public void layout(boolean changed, int left, int top, int right, int bottom) {
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

        scrollX = viewGroup.getScrollX();
        scrollY = viewGroup.getScrollY();
    }

    public void draw(Canvas canvas) {
        if (!(drawable instanceof Animatable)) {
            int level = drawable.getLevel();
            if (level > DRAWABLE_MAX_LEVEL) {
                level = 0;
            }
            drawable.setLevel(level + DRAWABLE_LEVEL_INCREMENT);
        }

        int viewGroupWidth = viewGroup.getWidth();
        int viewGroupHeight = viewGroup.getHeight();

        int state;

        state = canvas.save();
        canvas.translate(viewGroupWidth / 2 - drawable.getIntrinsicWidth() / 2 + scrollX,
                viewGroupHeight / 2 - drawable.getIntrinsicHeight() / 2 + scrollY);
        drawable.draw(canvas);
        canvas.restoreToCount(state);

        if (textLayout != null) {
            state = canvas.save();
            canvas.translate(viewGroupWidth / 2 - textLayout.getWidth() / 2 + scrollX,
                    viewGroupHeight / 2 + drawable.getIntrinsicHeight() / 2 + scrollY);
            textLayout.draw(canvas);
            canvas.restoreToCount(state);
        }

        if (loading) {
            ViewCompat.postInvalidateOnAnimation(viewGroup);
        }
    }

    public boolean verifyDrawable(Drawable drawable) {
        return drawable == this.drawable;
    }

}