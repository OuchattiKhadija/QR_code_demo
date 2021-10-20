package net.maribat.qrtoscan;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserClientApi {
    private static final String BASE_URL = "https://qrscanner-mongodb-api.herokuapp.com/api/v1/users/";
    private ApiInterface apiInterface;
    private static UserClientApi INSTANCE;

    public UserClientApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static UserClientApi getINSTANCE() {
        if (null == INSTANCE){
            INSTANCE = new UserClientApi();
        }
        return INSTANCE;
    }

}
