package DAOImpl;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import java.util.List;
import DAO.UserDAO;
import model.User;
import util.RestService;

public class UserDAOImpl implements UserDAO {

    @Override
    public User findOne(Long id, String url) {
        return null;
    }

    @Override
    public List<User> findAll(String url) {
        return RestService.getInstance().exchange(UserDAO.BASE_URL + url, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        }).getBody();
    }

    @Override
    public User save(User user, String url) {
        return RestService.getInstance().postForObject(UserDAO.BASE_URL + url, user, User.class);
    }

    @Override
    public void delete(Long id, String url) {

    }
}
