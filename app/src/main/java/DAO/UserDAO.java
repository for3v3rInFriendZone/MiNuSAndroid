package DAO;

import java.util.List;
import model.Bill;
import model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import util.LoginData;


public interface UserDAO {

    String BASE_URL = "https://minus-server.herokuapp.com/";
   // String BASE_URL = "http://192.168.1.54:8080";

    @GET("/user")
    Call<List<User>> findAll();

    @GET("/user/userBills/{userId}")
    Call<List<Bill>> findUserBills(@Path("userId") Long userId);

    @POST("/user")
    Call<ResponseBody> save(@Body User u);

    @POST("/user/login")
    Call<User> login(@Body LoginData loginData);

    @PUT("/user")
    Call<User> editUser(@Body User editUser);

}
