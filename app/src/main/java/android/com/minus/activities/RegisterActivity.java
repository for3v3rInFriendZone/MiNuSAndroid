package android.com.minus.activities;

import android.com.minus.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class RegisterActivity extends AppCompatActivity {

    private EditText username, password, email;
    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button regButton = (Button)findViewById(R.id.regButton);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLOginPage(v);
            }
        });
    }

    public void toLOginPage(View v){

        username = (EditText) findViewById(R.id.usernameRegInput);
        password = (EditText) findViewById(R.id.passwordRegInput);
        email = (EditText) findViewById(R.id.emailRegInput);

        RequestParams params = new RequestParams();
        params.add("username", username.getText().toString());
        params.add("password", password.getText().toString());
        params.add("email", email.getText().toString());

        client.get("http://192.168.0.104:8080/user/all", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // If the response is JSONObject instead of expected JSONArray
                super.onSuccess(statusCode, headers, response);
                try {
                    response.get(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



        });

        Intent i = new Intent(this, LoginActivity.class);

        startActivity(i);
    }
}
