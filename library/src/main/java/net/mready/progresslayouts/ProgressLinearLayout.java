package net.mready.progresslayouts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import net.mready.progresslayouts.internal.ProgressLayoutHelper;

public class ProgressLinearLayout extends LinearLayout implements ProgressLayout {

    private final ProgressLayoutHelper progressLayoutHelper;
    private boolean loading = false;

    public ProgressLinearLayout(Context context) {
        this(context, null);
    }

    public ProgressLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

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
    public void setLoadingMessage(String message) {
        progressLayoutHelper.setLoadingMessage(message);
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