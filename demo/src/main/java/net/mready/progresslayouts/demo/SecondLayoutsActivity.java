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
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import net.mready.progresslayouts.ProgressLayout;

public class SecondLayoutsActivity extends AppCompatActivity {

    private ProgressLayout progressCoordinatorLayout;
    private ProgressLayout progressConstraintLayout;
    private ProgressLayout progressRelativeLayout;
    private ProgressLayout progressNestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layouts_second);

        progressCoordinatorLayout = findViewById(R.id.progress_coordinator_layout);
        progressConstraintLayout = findViewById(R.id.progress_constraint_layout);
        progressRelativeLayout = findViewById(R.id.progress_relative_layout);
        progressNestedScrollView = findViewById(R.id.progress_nested_scroll_view);

        progressCoordinatorLayout.setLoadingIndicatorColor(Color.WHITE);
        progressConstraintLayout.setLoadingIndicatorColor(Color.WHITE);
        progressRelativeLayout.setLoadingIndicatorColor(Color.WHITE);
        progressNestedScrollView.setLoadingIndicatorColor(Color.WHITE);

        findViewById(R.id.button_toggle_state).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleLoadingState(progressCoordinatorLayout);
                toggleLoadingState(progressConstraintLayout);
                toggleLoadingState(progressRelativeLayout);
                toggleLoadingState(progressNestedScrollView);
            }
        });
    }

    private void toggleLoadingState(ProgressLayout progressLayout) {
        boolean loading = progressLayout.isLoading();
        progressLayout.setLoading(!loading);
    }

}