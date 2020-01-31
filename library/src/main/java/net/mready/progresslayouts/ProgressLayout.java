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

import androidx.annotation.ArrayRes;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;


public interface ProgressLayout {

    /**
     * Sets the loading state of the layout.
     *
     * @param loading whether the layout should display the loading state or not
     * @since 1.0.0
     */
    void setLoading(boolean loading);

    /**
     * Whether the layout is currently in the loading state or not.
     *
     * @return <code>true</code> if the layout is in the loading state, <code>false</code> otherwise
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
     * @since 1.0.0
     */
    void setLoadingText(CharSequence text);

    /**
     * Displays a message below the loading indicator.
     * <p>
     * Can also be set using the <code>loadingText</code> XML attribute.
     * </p>
     *
     * @param textResId the message to be displayed, as a string resource reference
     * @since 1.0.0
     */
    void setLoadingText(@StringRes int textResId);

    /**
     * Sets the appearance of the message below the loading indicator.
     * <p>
     * Can also be set using the <code>loadingTextAppearance</code> XML attribute.
     * </p>
     *
     * @param resId the style to be used, as a resource reference
     * @since 1.0.0
     */
    void setLoadingTextAppearance(@StyleRes int resId);

    /**
     * Sets the color of the default loading indicator.
     * <p>
     * Can also be set using the <code>loadingIndicatorColor</code> XML attribute.
     * </p>
     *
     * @param colorResId the color to be used, as a resource reference
     * @since 1.0.0
     */
    void setLoadingIndicatorColorRes(@ColorRes int colorResId);

    /**
     * Sets the color of the default loading indicator.
     * <p>
     * Can also be set using the <code>loadingIndicatorColor</code> XML attribute.
     * </p>
     *
     * @param color the color to be used
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
     * @param colorArrayResId the color scheme to be used, as a resource reference to an array of colors
     * @since 1.0.0
     */
    void setLoadingIndicatorColorResScheme(@ArrayRes int colorArrayResId);

    /**
     * Sets the color scheme of the default loading indicator. When animating, the indicator
     * will go through every color from the provided color list.
     * <p>
     * Can also be set using the <code>loadingIndicatorColorScheme</code> XML attribute.
     * </p>
     *
     * @param colorsResIds the color scheme to be used, as an array of color resource references
     * @since 1.0.0
     */
    void setLoadingIndicatorColorResScheme(@NonNull int[] colorsResIds);

    /**
     * Sets the color scheme of the default loading indicator. When animating, the indicator
     * will go through every color from the provided color list.
     * <p>
     * Can also be set using the <code>loadingIndicatorColorScheme</code> XML attribute.
     * </p>
     *
     * @param colors the color scheme to be used, as an array of colors
     * @since 1.0.0
     */
    void setLoadingIndicatorColorScheme(@NonNull int[] colors);

    /**
     * Sets a custom drawable as the loading indicator.
     * <p>
     * Can also be set using the <code>loadingIndicatorDrawable</code> XML attribute.
     * </p>
     *
     * @param drawableResId the drawable to be used, as a resource reference
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
     * @since 1.0.0
     */
    void setLoadingIndicatorDrawable(Drawable drawable);

}