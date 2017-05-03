package DAO;

import java.util.List;
import model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserDAO {

    String BASE_URL = "http://192.168.1.54:8080";

    @GET("/user")
    Call<List<User>> findAll();

    @POST("/user")
    Call<ResponseBody> save(@Body User u);

}
