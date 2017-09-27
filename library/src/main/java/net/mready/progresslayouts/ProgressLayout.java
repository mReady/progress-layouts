package net.mready.progresslayouts;

import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;

public interface ProgressLayout {

    /**
     * Sets the loading state of the layout
     *
     * @param loading the loading state to be used
     * @see #isLoading()
     * @since 1.0.0
     */
    void setLoading(boolean loading);

    /**
     * Whether the layout is currently in the loading state or not
     *
     * @return <code>true</code> if the layout is in the loading state
     * <code>false</code> otherwise
     * @see #setLoading(boolean)
     * @since 1.0.0
     */
    boolean isLoading();

    /**
     * Displays a message below the loading indicator.
     * <p>
     * Can also be set using the <code>loadingText</code> XML attribute.
     * </p>
     *
     * @param text the message to be displayed
     * @see #setLoadingText(int)
     * @see #setLoadingTextAppearance(int)
     * @since 1.0.0
     */
    void setLoadingText(CharSequence text);

    /**
     * Displays a message below the loading indicator.
     * <p>
     * Can also be set using the <code>loadingText</code> XML attribute.
     * </p>
     *
     * @param resId the String resource reference of the message to be display
     * @see #setLoadingText(CharSequence)
     * @see #setLoadingTextAppearance(int)
     * @since 1.0.0
     */
    void setLoadingText(@StringRes int resId);

    /**
     * Sets the appearance of the message below the loading indicator.
     * <p>
     * Can also be set using the <code>loadingTextAppearance</code> XML attribute.
     * </p>
     *
     * @param resId the style resource reference to be used
     * @see #setLoadingText(int)
     * @see #setLoadingText(CharSequence)
     * @since 1.0.0
     */
    void setLoadingTextAppearance(@StyleRes int resId);

    /**
     * Sets the color of the default loading indicator.
     * <p>
     * Can also be set using the <code>loadingIndicatorColor</code> XML attribute.
     * </p>
     *
     * @param color the color to be used
     * @see #setLoadingIndicatorColorScheme(int[])
     * @since 1.0.0
     */
    void setLoadingIndicatorColor(@ColorInt int color);

    /**
     * Sets the color scheme of the default loading indicator. When animating, the indicator
     * will go through every color from the provided color list.
     * <p>
     * Can also be set using the <code>loadingIndicatorColorScheme</code> XML attribute.
     * </p>
     *
     * @param colors the color scheme to be used
     * @see #setLoadingIndicatorColor(int)
     * @since 1.0.0
     */
    void setLoadingIndicatorColorScheme(@NonNull @ArrayRes int[] colors);

    /**
     * Sets a custom drawable as the loading indicator.
     * <p>
     * Can also be set using the <code>loadingIndicatorDrawable</code> XML attribute.
     * </p>
     *
     * @param resId the drawable resource reference to be used
     * @see #setLoadingIndicatorDrawable(Drawable)
     * @since 1.0.0
     */
    void setLoadingIndicatorDrawable(@DrawableRes int resId);

    /**
     * Sets a custom drawable as the loading indicator.
     * <p>
     * Can also be set using the <code>loadingIndicatorDrawable</code> XML attribute.
     * </p>
     *
     * @param drawable the Drawable to be used
     * @see #setLoadingIndicatorDrawable(int)
     * @since 1.0.0
     */
    void setLoadingIndicatorDrawable(Drawable drawable);

}