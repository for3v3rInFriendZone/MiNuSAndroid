package android.com.minus.activities;

import android.com.minus.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_login);

        Button login = (Button) findViewById(R.id.loginButton);
        Button regButton = (Button) findViewById(R.id.register);

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
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void toRegPage(View v) {
        Intent i = new Intent(this, RegisterActiviti.class);
        startActivity(i);
    }
}
