package DAO;

import java.util.List;

import model.User;

public interface UserDAO {

    public static final String BASE_URL = "http://192.168.1.54:8080/user/";

    public User findOne(Long id, String url);

    public List<User> findAll(String url);

    public User save(User user, String url);

    public void delete(Long id, String url);
}
