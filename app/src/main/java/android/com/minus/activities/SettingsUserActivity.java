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

import DAO.UserDAO;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import util.RetrofitBuilder;
import util.SharedSession;


public class SettingsUserActivity extends AppCompatActivity {

    private Button submit_changes;
    private EditText firstname;
    private EditText lastname;
    private EditText username;
    private EditText email;
    private User logedUser;
    private UserDAO userDao;
    private Retrofit retrofit;

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

        submit_changes = (Button) findViewById(R.id.submit_changes);
        firstname = (EditText) findViewById(R.id.name_info);
        lastname = (EditText) findViewById(R.id.surname_info);
        username = (EditText) findViewById(R.id.username_info);
        email = (EditText) findViewById(R.id.email_info);

        retrofit = RetrofitBuilder.getInstance(UserDAO.BASE_URL);
        userDao = retrofit.create(UserDAO.class);

        logedUser = SharedSession.getSavedObjectFromPreference(getApplicationContext(), "userSession", "user", User.class);

        firstname.setText(logedUser.getFirstname());
        lastname.setText(logedUser.getLastname());
        username.setText(logedUser.getUsername());
        email.setText(logedUser.getEmail());

        submit_changes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logedUser.setFirstname(firstname.getText().toString());
                logedUser.setLastname(lastname.getText().toString());
                userDao.editUser(logedUser).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()) {
                            SharedSession.saveObjectToSharedPreference(getApplicationContext(), "userSession", "user", response.body());
                            NavUtils.navigateUpTo(SettingsUserActivity.this, new Intent(SettingsUserActivity.this, SettingsActivity.class));
                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });

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