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

import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;

public interface ProgressLayout {

    /**
     * Sets the loading state of the layout.
     *
     * @param loading whether the layout should display the loading state or not
     * @see #isLoading()
     * @since 1.0.0
     */
    void setLoading(boolean loading);

    /**
     * Whether the layout is currently in the loading state or not.
     *
     * @return <code>true</code> if the layout is in the loading state, <code>false</code> otherwise
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
     * @param textResId the string's resource reference of the message to be displayed
     * @see #setLoadingText(CharSequence)
     * @see #setLoadingTextAppearance(int)
     * @since 1.0.0
     */
    void setLoadingText(@StringRes int textResId);

    /**
     * Sets the appearance of the message below the loading indicator.
     * <p>
     * Can also be set using the <code>loadingTextAppearance</code> XML attribute.
     * </p>
     *
     * @param resId the style's resource reference to be used
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
     * @see #setLoadingIndicatorColorRes(int)
     * @see #setLoadingIndicatorColorScheme(int[])
     * @since 1.0.0
     */
    void setLoadingIndicatorColor(@ColorInt int color);

    /**
     * Sets the color of the default loading indicator.
     * <p>
     * Can also be set using the <code>loadingIndicatorColor</code> XML attribute.
     * </p>
     *
     * @param colorResId the color's resource reference to be used
     * @see #setLoadingIndicatorColor(int)
     * @see #setLoadingIndicatorColorScheme(int[])
     * @since 1.0.0
     */
    void setLoadingIndicatorColorRes(@ColorRes int colorResId);

    /**
     * Sets the color scheme of the default loading indicator. When animating, the indicator
     * will go through every color from the provided color list.
     * <p>
     * Can also be set using the <code>loadingIndicatorColorScheme</code> XML attribute.
     * </p>
     *
     * @param colors the color scheme to be used, as an array of colors
     * @see #setLoadingIndicatorColorResScheme(int[])
     * @see #setLoadingIndicatorColor(int)
     * @see #setLoadingIndicatorColorRes(int)
     * @since 1.0.0
     */
    void setLoadingIndicatorColorScheme(@NonNull @ArrayRes int[] colors);

    /**
     * Sets the color scheme of the default loading indicator. When animating, the indicator
     * will go through every color from the provided color list.
     * <p>
     * Can also be set using the <code>loadingIndicatorColorScheme</code> XML attribute.
     * </p>
     *
     * @param colorsResIds the color scheme to be used, as an array of color resource references
     * @see #setLoadingIndicatorColorScheme(int[])
     * @see #setLoadingIndicatorColor(int)
     * @see #setLoadingIndicatorColorRes(int)
     * @since 1.0.0
     */
    void setLoadingIndicatorColorResScheme(@NonNull @ArrayRes int[] colorsResIds);

    /**
     * Sets a custom drawable as the loading indicator.
     * <p>
     * Can also be set using the <code>loadingIndicatorDrawable</code> XML attribute.
     * </p>
     *
     * @param drawableResId the drawable's resource reference to be used
     * @see #setLoadingIndicatorDrawable(Drawable)
     * @since 1.0.0
     */
    void setLoadingIndicatorDrawable(@DrawableRes int drawableResId);

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