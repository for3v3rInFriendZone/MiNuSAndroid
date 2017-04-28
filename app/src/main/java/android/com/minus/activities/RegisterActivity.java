package android.com.minus.activities;

import android.com.minus.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import AsyncService.GetUserTask;
import model.User;
import util.RestService;

public class RegisterActivity extends AppCompatActivity {

    private GetUserTask userTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userTask = new GetUserTask(this);
        Button regButton = (Button)findViewById(R.id.regButton);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // userTask.execute();
                registerNewUser();
            }
        });
    }

    public void registerNewUser() {
        EditText firstName = (EditText) findViewById(R.id.nameRegInput);
        EditText lastname = (EditText) findViewById(R.id.surnameRegInput);
        EditText username = (EditText) findViewById(R.id.usernameRegInput);
        EditText password = (EditText) findViewById(R.id.passwordRegInput);
        EditText email = (EditText) findViewById(R.id.emailRegInput);

        userTask.execute(firstName.getText().toString(), lastname.getText().toString(),
                username.getText().toString(), password.getText().toString(), email.getText().toString());


        Intent i = new Intent(this, LoginActivity.class);

        startActivity(i);

    }
}
