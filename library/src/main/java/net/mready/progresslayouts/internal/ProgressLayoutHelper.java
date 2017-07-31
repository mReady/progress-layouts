package net.mready.progresslayouts.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;

import com.facebook.fbui.textlayoutbuilder.ResourceTextLayoutHelper;
import com.facebook.fbui.textlayoutbuilder.TextLayoutBuilder;

import net.mready.progresslayouts.R;

public class ProgressLayoutHelper {

    private static final int DRAWABLE_MAX_LEVEL = 10000;
    private static final int DRAWABLE_LEVEL_INCREMENT = 50;

    private final ViewGroup viewGroup;

    private Drawable drawable;
    private Layout textLayout;
    private int textAppearance;
    private boolean loading;

    public ProgressLayoutHelper(final ViewGroup viewGroup, AttributeSet attrs) {
        this.viewGroup = viewGroup;

        Context context = viewGroup.getContext();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProgressLayout,
                R.attr.progressLayoutStyle, 0);

        if (!viewGroup.isInEditMode()) {
            drawable = a.getDrawable(R.styleable.ProgressLayout_progressDrawable);

            if (drawable == null) {
                drawable = new MaterialProgressDrawable(context);

                int colorListRes = a.getResourceId(R.styleable.ProgressLayout_progressColorList, -1);
                if (colorListRes != -1) {
                    int[] colorList = context.getResources().getIntArray(colorListRes);
                    ((MaterialProgressDrawable) drawable).setColorSchemeColors(colorList);
                } else {
                    int defaultColor = Color.BLUE;
                    TypedValue value = new TypedValue();
                    if (context.getTheme().resolveAttribute(R.attr.colorAccent, value, true)) {
                        defaultColor = value.data;
                    }

                    int color = a.getColor(R.styleable.ProgressLayout_progressColor, defaultColor);
                    ((MaterialProgressDrawable) drawable).setColorSchemeColors(color);
                }
            }

            TextLayoutBuilder builder = new TextLayoutBuilder()
                    .setText(a.getText(R.styleable.ProgressLayout_android_text));

            textAppearance = a.getResourceId(R.styleable.ProgressLayout_android_textAppearance, -1);
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

    public void setLoadingMessage(String message) {
        TextLayoutBuilder builder = new TextLayoutBuilder().setText(message);
        if (textAppearance != -1) {
            ResourceTextLayoutHelper.setTextAppearance(builder, viewGroup.getContext(), textAppearance);
        }
        textLayout = builder.build();
    }

    public void measure(int widthMeasureSpec, int heightMeasureSpec) {
        // Nothing right now
    }

    public void layout(boolean changed, int left, int top, int right, int bottom) {
        int drawableLeft = viewGroup.getWidth() / 2 - drawable.getIntrinsicWidth() / 2;
        int drawableTop = viewGroup.getHeight() / 2 - drawable.getIntrinsicHeight() / 2;

        drawable.setBounds(drawableLeft,
                drawableTop,
                drawableLeft + drawable.getIntrinsicWidth(),
                drawableTop + drawable.getIntrinsicHeight());
    }

    public void draw(Canvas canvas) {
        if (!(drawable instanceof Animatable)) {
            int level = drawable.getLevel();
            if (level > DRAWABLE_MAX_LEVEL) {
                level = 0;
            }
            drawable.setLevel(level + DRAWABLE_LEVEL_INCREMENT);
        }

        drawable.draw(canvas);

        if (textLayout != null) {
            int state = canvas.save();

            canvas.translate(canvas.getWidth() / 2 - textLayout.getWidth() / 2,
                    canvas.getHeight() / 2 + drawable.getIntrinsicHeight() / 2);
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