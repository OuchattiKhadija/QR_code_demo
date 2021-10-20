package net.maribat.qrtoscan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {


    @GET("users/users/{id}")
    public Call<User> getUserById(@Path("id") String postId);

    @POST("users")
    public Call<User> storeUser(@Body User user);
}
