package android.com.minus.activities;

import android.com.minus.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class RegisterActivity extends AppCompatActivity implements Callback<List<User>>{

    private UserDAO userDao;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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

        userDao.findAll().enqueue(this);

      //  Intent i = new Intent(this, LoginActivity.class);
      //  startActivity(i);
    }


    @Override
    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
        if(response.isSuccessful()) {

            EditText username = (EditText) findViewById(R.id.nameRegInput);
            username.setText(response.body().get(0).getUsername());
        } else {

        }
    }

    @Override
    public void onFailure(Call<List<User>> call, Throwable t) {

    }
}
