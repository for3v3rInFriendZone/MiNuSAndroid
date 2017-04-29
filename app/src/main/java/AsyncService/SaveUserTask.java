package AsyncService;

import android.app.Activity;
import android.com.minus.activities.LoginActivity;
import android.content.Intent;
import android.os.AsyncTask;

import DAO.UserDAO;
import model.User;

public class SaveUserTask extends AsyncTask<String, Void, Void> {

    private Activity activity;
    private UserDAO userDAO;

    public SaveUserTask(Activity activity, UserDAO userDAO) {
        this.activity = activity;
        this.userDAO = userDAO;
    }

    @Override
    protected Void doInBackground(String... params) {

        User newUser = new User(params[0], params[1], params[2], params[3], params[4]);
        userDAO.save(newUser, "");

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Intent i = new Intent(activity, LoginActivity.class);
        activity.startActivity(i);
    }
}
