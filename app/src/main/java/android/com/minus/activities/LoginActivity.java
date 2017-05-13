package android.com.minus.activities;

import android.com.minus.R;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import DAO.UserDAO;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import util.LoginData;
import util.RetrofitBuilder;
import util.SharedSession;

public class LoginActivity extends AppCompatActivity{

    private UserDAO userDao;
    private Retrofit retrofit;
    private Button login, regButton;
    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.loginButton);
        regButton = (Button) findViewById(R.id.register);

        username = (EditText) findViewById(R.id.usernameInput);
        password = (EditText) findViewById(R.id.passwordInput);

        retrofit = RetrofitBuilder.getInstance(UserDAO.BASE_URL);
        userDao = retrofit.create(UserDAO.class);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPage(v);
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRegPage(v);
            }
        });

    }

    public void mainPage(View v) {

        if(isValid()){
            LoginData loginData = new LoginData(username.getText().toString(), password.getText().toString());

            userDao.login(loginData).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        if(response.body() != null) {
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            User user = response.body();
                            SharedSession.saveObjectToSharedPreference(getApplicationContext(), "userSession", "user", user);
                            startActivity(i);
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "Korisnik nije pronađen", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Korisnik nije pronađen", Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    public void toRegPage(View v) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    private boolean isValid(){
        if(username.getText().toString().length() == 0) {
            username.setError("Morate uneti korisničko ime.");
            return false;
        } else if(password.getText().toString().length() < 7) {
            password.setError("Lozinka mora sadržati barem 7 karaktera.");
            return false;
        }

        return true;
    }
}
