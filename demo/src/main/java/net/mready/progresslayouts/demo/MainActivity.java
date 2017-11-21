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

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import net.mready.progresslayouts.ProgressLayout;

public class MainActivity extends AppCompatActivity {

    // TODO: Rename
    private ProgressLayout progressLayout1;
    private ProgressLayout progressLayout2;
    private ProgressLayout progressLayout3;
    private ProgressLayout progressLayout4;
    private ProgressLayout progressLayout5;
    private ProgressLayout progressLayout6;
    private ProgressLayout progressLayout7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupProgressLayouts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toggle:
                toggleLoadingState(progressLayout1);
                toggleLoadingState(progressLayout2);
                toggleLoadingState(progressLayout3);
                toggleLoadingState(progressLayout4);
                toggleLoadingState(progressLayout5);
                toggleLoadingState(progressLayout6);
                toggleLoadingState(progressLayout7);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupProgressLayouts() {
        progressLayout1 = findViewById(R.id.progress_layout_1);
        progressLayout2 = findViewById(R.id.progress_layout_2);
        progressLayout3 = findViewById(R.id.progress_layout_3);
        progressLayout4 = findViewById(R.id.progress_layout_4);
        progressLayout5 = findViewById(R.id.progress_layout_5);
        progressLayout6 = findViewById(R.id.progress_layout_6);
        progressLayout7 = findViewById(R.id.progress_layout_7);

        progressLayout1.setLoading(true);
        progressLayout2.setLoading(true);
        progressLayout3.setLoading(true);
        progressLayout4.setLoading(true);
        progressLayout5.setLoading(true);
        progressLayout6.setLoading(true);
        progressLayout7.setLoading(true);

        // Only to demonstrate different customization options
        if (false) {
            int[] colorsResIds = {
                    R.color.color1,
                    R.color.color2,
                    R.color.color3
            };

            int[] colors = {
                    ContextCompat.getColor(this, R.color.color1),
                    ContextCompat.getColor(this, R.color.color2),
                    ContextCompat.getColor(this, R.color.color3)
            };

            progressLayout1.setLoadingIndicatorColorRes(R.color.colorAccent);
            progressLayout1.setLoadingIndicatorColor(Color.parseColor("00ff00"));
            progressLayout1.setLoadingIndicatorColor(ContextCompat.getColor(this, R.color.colorAccent));

            progressLayout1.setLoadingIndicatorColorResScheme(R.array.loader_colors);
            progressLayout1.setLoadingIndicatorColorResScheme(colorsResIds);
            progressLayout1.setLoadingIndicatorColorScheme(getResources().getIntArray(R.array.loader_colors));
            progressLayout1.setLoadingIndicatorColorScheme(colors);

            progressLayout1.setLoadingIndicatorDrawable(R.drawable.loader_circle);
            progressLayout1.setLoadingIndicatorDrawable(ContextCompat.getDrawable(this, R.drawable.loader_circle));

            progressLayout1.setLoadingText("Please wait");
            progressLayout1.setLoadingText(R.string.message_loading);
            progressLayout1.setLoadingTextAppearance(R.style.AppTheme_Text_Loader);
        }
    }

    private void toggleLoadingState(ProgressLayout progressLayout) {
        boolean loading = progressLayout.isLoading();
        progressLayout.setLoading(!loading);
    }

}