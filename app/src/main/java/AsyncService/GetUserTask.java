package AsyncService;

import android.app.Activity;
import android.com.minus.activities.LoginActivity;
import android.content.Intent;
import android.os.AsyncTask;
import model.User;
import util.RestService;

public class GetUserTask extends AsyncTask<String, Void, Void> {

    private static final String BASE_URL = "http://192.168.1.54:8080/user";
    private Activity activity;

    public GetUserTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(String... params) {

        User newUser = new User(params[0], params[1], params[2], params[3], params[4]);
        RestService.getInstance().postForObject(BASE_URL, newUser, User.class);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Intent i = new Intent(activity, LoginActivity.class);
        activity.startActivity(i);
    }
}
