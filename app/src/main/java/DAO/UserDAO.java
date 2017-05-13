package DAO;

import java.util.List;
import model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import util.LoginData;

public interface UserDAO {

    String BASE_URL = "http://192.168.0.104:8080";

    @GET("/user")
    Call<List<User>> findAll();

    @POST("/user")
    Call<ResponseBody> save(@Body User u);

    @POST("/user/login")
    Call<User> login(@Body LoginData loginData);

}
