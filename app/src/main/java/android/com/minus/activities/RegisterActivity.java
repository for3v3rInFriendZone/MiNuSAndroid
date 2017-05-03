package android.com.minus.activities;

import android.com.minus.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.GsonBuilder;

import DAO.UserDAO;
import model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity implements Callback<ResponseBody>{

    private UserDAO userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button regButton = (Button)findViewById(R.id.regButton);
        createUserDAO();

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLOginPage(v);
            }
        });
    }

    public void toLOginPage(View v){

        userDao.save(new User("wwww", "ssss", "aaaaa", "fffff", "bbbbbbbb4")).enqueue(this);

        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    private void createUserDAO() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserDAO.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .build();

        userDao = retrofit.create(UserDAO.class);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        if(response.isSuccessful()) {
            Toast.makeText(RegisterActivity.this, "Upvote successful", Toast.LENGTH_LONG).show();
        } else {
            Log.d("QuestionsCallback", "Code: " + response.code() + " Message: " + response.message());
        }
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        t.printStackTrace();
    }
}
