package DAO;

import java.util.List;

import model.Budget;
import model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BudgetDAO {

    @GET("/budget")
    Call<List<Budget>> findAll();

    @POST("/budget")
    Call<ResponseBody> save(@Body Budget budget);

    @GET("/budget/{id}")
    Call<Budget> getOne(@Path("id")Long id);

    @GET("/budget/user/{id}")
    Call<List<Budget>> findUserBudgets(@Path("id") Long id);

    @PUT("/budget/update}")
    Call<Budget> update(@Body Budget budget);
}
