package android.com.minus.activities;

import android.com.minus.R;
import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import fragments.SettingsFragment;


public class SettingsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar_settings);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
        }
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {

            getFragmentManager().beginTransaction().replace(R.id.settings_container,
                    new SettingsFragment()).commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(SettingsActivity.this, new Intent(SettingsActivity.this, MainActivity.class));
                break;
        }
        return true;
    }

}
