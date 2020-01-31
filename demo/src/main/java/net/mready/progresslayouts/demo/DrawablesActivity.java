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

package net.mready.progresslayouts.demo;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.OnCompositionLoadedListener;

import net.mready.progresslayouts.ProgressLayout;

public class DrawablesActivity extends AppCompatActivity {

    private ProgressLayout layoutRotateDrawable;
    private ProgressLayout layoutRotateDrawableWithText;
    private ProgressLayout layoutMaterialDrawable;
    private ProgressLayout layoutMaterialDrawableWithColor;
    private ProgressLayout layoutMaterialDrawableWithColorScheme;
    private ProgressLayout layoutAnimationDrawable;
    private ProgressLayout layoutLottieDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawables);

        setupProgressLayouts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_drawables, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toggle:
                toggleLoadingState(layoutRotateDrawable);
                toggleLoadingState(layoutRotateDrawableWithText);
                toggleLoadingState(layoutMaterialDrawable);
                toggleLoadingState(layoutMaterialDrawableWithColor);
                toggleLoadingState(layoutMaterialDrawableWithColorScheme);
                toggleLoadingState(layoutAnimationDrawable);
                toggleLoadingState(layoutLottieDrawable);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupProgressLayouts() {
        layoutRotateDrawable = findViewById(R.id.layout_rotate_drawable);
        layoutRotateDrawableWithText = findViewById(R.id.layout_rotate_drawable_text);
        layoutMaterialDrawable = findViewById(R.id.layout_material_drawable);
        layoutMaterialDrawableWithColor = findViewById(R.id.layout_material_drawable_color);
        layoutMaterialDrawableWithColorScheme = findViewById(R.id.layouts_material_drawable_color_scheme);
        layoutAnimationDrawable = findViewById(R.id.layout_animation_drawable);
        layoutLottieDrawable = findViewById(R.id.layout_lottie_drawable);

        layoutRotateDrawable.setLoading(true);
        layoutRotateDrawableWithText.setLoading(true);
        layoutMaterialDrawable.setLoading(true);
        layoutMaterialDrawableWithColor.setLoading(true);
        layoutMaterialDrawableWithColorScheme.setLoading(true);
        layoutAnimationDrawable.setLoading(true);
        layoutLottieDrawable.setLoading(true);

        layoutLottieDrawable.setLoadingIndicatorDrawable(createLottieDrawable("PinJump.json"));
    }

    private LottieDrawable createLottieDrawable(String filename) {
        final LottieDrawable lottieDrawable = new LottieDrawable();

        LottieComposition.Factory.fromAssetFileName(this, filename,
                new OnCompositionLoadedListener() {
                    @Override
                    public void onCompositionLoaded(@Nullable LottieComposition composition) {
                        lottieDrawable.setComposition(composition);
                        lottieDrawable.setRepeatCount(ValueAnimator.INFINITE);
                        lottieDrawable.playAnimation();
                    }
                });

        return lottieDrawable;
    }

    private void toggleLoadingState(ProgressLayout progressLayout) {
        boolean loading = progressLayout.isLoading();
        progressLayout.setLoading(!loading);
    }

}