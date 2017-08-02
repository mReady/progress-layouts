package net.mready.progresslayouts;

import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;

public interface ProgressLayout {

    void setLoading(boolean loading);

    boolean isLoading();

    void setLoadingText(CharSequence text);

    void setLoadingText(@StringRes int resId);

    void setLoadingTextAppearance(@StyleRes int resId);

    void setLoadingIndicatorColor(@ColorInt int color);

    void setLoadingIndicatorColorScheme(@NonNull @ArrayRes int[] colors);

    void setLoadingIndicatorDrawable(@DrawableRes int resId);

    void setLoadingIndicatorDrawable(Drawable drawable);

}