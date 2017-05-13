package android.com.minus.activities;

import android.com.minus.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import DAO.UserDAO;
import model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import util.RetrofitBuilder;

public class RegisterActivity extends AppCompatActivity implements Callback<ResponseBody>{

    private UserDAO userDao;
    private Retrofit retrofit;
    private EditText firstname, lastname, username, password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstname = (EditText) findViewById(R.id.nameRegInput);
        lastname = (EditText) findViewById(R.id.surnameRegInput);
        username = (EditText) findViewById(R.id.usernameRegInput);
        password = (EditText) findViewById(R.id.passwordRegInput);
        email = (EditText) findViewById(R.id.emailRegInput);

        Button regButton = (Button)findViewById(R.id.regButton);
        retrofit = RetrofitBuilder.getInstance(UserDAO.BASE_URL);
        userDao = retrofit.create(UserDAO.class);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLOginPage(v);
            }
        });
    }

    public void toLOginPage(View v){

        if(isValid()) {
            User user = new User(username.getText().toString(), password.getText().toString(),
                    email.getText().toString(), firstname.getText().toString(),
                    lastname.getText().toString());

            userDao.save(user).enqueue(this);
        }

        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    public boolean isValid() {
        if(firstname.getText().toString().length() == 0) {
            firstname.setError("Morate uneti ime.");
            return false;
        } else if(lastname.getText().toString().length() == 0) {
            lastname.setError("Morate uneti prezime.");
            return false;
        } else if(username.getText().toString().length() == 0) {
            username.setError("Morate uneti korisničko ime.");
            return false;
        } else if(password.getText().toString().length() < 7) {
            password.setError("Lozinka mora sadržati barem 7 karaktera.");
            return false;
        } else if(email.getText().toString().length() == 0) {
            email.setError("Morate uneti email.");
            return false;
        }

        return true;
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if(response.isSuccessful()) {
            Toast.makeText(getApplicationContext(), "Uspesno je dodan korisnik.",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), response.message(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        t.printStackTrace();
    }

}
