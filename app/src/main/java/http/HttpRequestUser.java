package http;

import android.os.AsyncTask;
import android.util.Log;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import model.User;

public class HttpRequestUser  extends AsyncTask<Void, Void, User> {

    @Override
    protected User doInBackground(Void... params) {

        try {
            final String url = "http://192.168.56.1:8080/user/all";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            User user = restTemplate.getForObject(url, User.class);
            return user;
        } catch (Exception e) {
            Log.e("MainActivity", e.getMessage(), e);
        }

        return null;
    }
}
