package AsyncService;

import android.os.AsyncTask;
import java.util.List;
import DAO.UserDAO;
import model.User;

public class GetUsersTask extends AsyncTask<Void, Void, List<User>> {

    private UserDAO userDAO;

    public GetUsersTask(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    protected List<User> doInBackground(Void... params) {
        return userDAO.findAll("");
    }

    @Override
    protected void onPostExecute(List<User> users) {
       List<User> asd = users;
    }
}
