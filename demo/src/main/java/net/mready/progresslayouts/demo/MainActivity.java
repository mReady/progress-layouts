package net.mready.progresslayouts.demo;

import android.os.Bundle;
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

        testProgressLayouts();
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
                toggleLoading(((ProgressLayout) findViewById(R.id.progress_layout_1)));
                toggleLoading(((ProgressLayout) findViewById(R.id.progress_layout_2)));
                toggleLoading(((ProgressLayout) findViewById(R.id.progress_layout_3)));
                toggleLoading(((ProgressLayout) findViewById(R.id.progress_layout_4)));
                toggleLoading(((ProgressLayout) findViewById(R.id.progress_layout_5)));
                toggleLoading(((ProgressLayout) findViewById(R.id.progress_layout_6)));
                toggleLoading(((ProgressLayout) findViewById(R.id.progress_layout_7)));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void testProgressLayouts() {
        ((ProgressLayout) findViewById(R.id.progress_layout_1)).setLoading(true);

        ((ProgressLayout) findViewById(R.id.progress_layout_2)).setLoading(true);
        ((ProgressLayout) findViewById(R.id.progress_layout_2)).setLoadingMessage("Loading");

        ((ProgressLayout) findViewById(R.id.progress_layout_3)).setLoading(true);
        ((ProgressLayout) findViewById(R.id.progress_layout_4)).setLoading(true);
        ((ProgressLayout) findViewById(R.id.progress_layout_5)).setLoading(true);
        ((ProgressLayout) findViewById(R.id.progress_layout_6)).setLoading(true);
        ((ProgressLayout) findViewById(R.id.progress_layout_7)).setLoading(true);
    }

    private void toggleLoading(ProgressLayout progressLayout) {
        boolean loading = progressLayout.isLoading();
        progressLayout.setLoading(!loading);
    }

}