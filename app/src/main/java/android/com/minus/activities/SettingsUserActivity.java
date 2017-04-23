package android.com.minus.activities;

import android.com.minus.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SettingsUserActivity extends AppCompatActivity {

    private Button change_password;
    private EditText users_password;
    private Button submit_changes;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar_setting_user);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
        }
        actionBar.setDisplayHomeAsUpEnabled(true);

        change_password = (Button) findViewById(R.id.change_password);
        users_password = (EditText) findViewById(R.id.password_info);
        submit_changes = (Button) findViewById(R.id.submit_changes);

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                users_password.setVisibility(View.VISIBLE);
            }
        });

        submit_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpTo(SettingsUserActivity.this, new Intent(SettingsUserActivity.this, SettingsActivity.class));
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpTo(SettingsUserActivity.this, new Intent(SettingsUserActivity.this, SettingsActivity.class));
                break;
        }
        return true;
    }
}
