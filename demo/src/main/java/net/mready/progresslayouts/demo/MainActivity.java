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
                toggleLoadingState(((ProgressLayout) findViewById(R.id.progress_layout_1)));
                toggleLoadingState(((ProgressLayout) findViewById(R.id.progress_layout_2)));
                toggleLoadingState(((ProgressLayout) findViewById(R.id.progress_layout_3)));
                toggleLoadingState(((ProgressLayout) findViewById(R.id.progress_layout_4)));
                toggleLoadingState(((ProgressLayout) findViewById(R.id.progress_layout_5)));
                toggleLoadingState(((ProgressLayout) findViewById(R.id.progress_layout_6)));
                toggleLoadingState(((ProgressLayout) findViewById(R.id.progress_layout_7)));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupProgressLayouts() {
        ((ProgressLayout) findViewById(R.id.progress_layout_1)).setLoading(true);
        ((ProgressLayout) findViewById(R.id.progress_layout_2)).setLoading(true);
        ((ProgressLayout) findViewById(R.id.progress_layout_3)).setLoading(true);
        ((ProgressLayout) findViewById(R.id.progress_layout_4)).setLoading(true);
        ((ProgressLayout) findViewById(R.id.progress_layout_5)).setLoading(true);
        ((ProgressLayout) findViewById(R.id.progress_layout_6)).setLoading(true);
        ((ProgressLayout) findViewById(R.id.progress_layout_7)).setLoading(true);

        if (false) {
            int[] colors = {
                    ContextCompat.getColor(this, R.color.color1),
                    ContextCompat.getColor(this, R.color.color2),
                    ContextCompat.getColor(this, R.color.color3)
            };

            ProgressLayout progressLayout = findViewById(R.id.progress_layout_1);

            progressLayout.setLoadingIndicatorColor(Color.parseColor("00ff00"));
            progressLayout.setLoadingIndicatorColor(ContextCompat.getColor(this, R.color.colorAccent));
            progressLayout.setLoadingIndicatorColorScheme(getResources().getIntArray(R.array.loader_colors));
            progressLayout.setLoadingIndicatorColorScheme(colors);
            progressLayout.setLoadingIndicatorDrawable(R.drawable.loader_circle);
            progressLayout.setLoadingIndicatorDrawable(ContextCompat.getDrawable(this, R.drawable.loader_circle));
            progressLayout.setLoadingText("Please wait");
            progressLayout.setLoadingText(R.string.app_name);
            progressLayout.setLoadingTextAppearance(R.style.AppTheme_Text_Loader);
        }
    }

    private void toggleLoadingState(ProgressLayout progressLayout) {
        boolean loading = progressLayout.isLoading();
        progressLayout.setLoading(!loading);
    }

}