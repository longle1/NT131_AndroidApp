package com.example.myprojectnt131;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public class ApiService {
    private static final String BASE_URL = "https://api-server-nt131.onrender.com/api/v1/users/";
    private static ApiService instance;

    private ApiInterface apiInterface;

    private ApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static ApiService getInstance() {
        if (instance == null) {
            synchronized (ApiService.class) {
                if (instance == null) {
                    instance = new ApiService();
                }
            }
        }
        return instance;
    }

    public ApiInterface getApiInterface() {
        return apiInterface;
    }

    public interface ApiInterface {
        @GET("login")
        Call<LoginRequest> login(@Query("username") String username, @Query("password") String password);

        @PUT("status-door")
        Call<Result> updateStatusDoor(@Body StatusDoor request);
    }
}