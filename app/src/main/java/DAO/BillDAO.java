package DAO;

import java.util.List;
import model.Bill;
import model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface BillDAO {

    @GET("/bill")
    Call<List<User>> findAll();

    @GET("/bill/{billId}")
    Call<Bill> findOne(@Path("billId") Long billId);

    @POST("/bill")
    Call<ResponseBody> save(@Body Bill b);

    @DELETE("/bill/{billId}")
    Call<ResponseBody> delete(@Path("billId") Long billId);
}
