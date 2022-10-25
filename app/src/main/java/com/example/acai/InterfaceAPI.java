package com.example.acai;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface InterfaceAPI {
    @POST("v1/users/signin")
    Call<String> checkLogin(@Header("Authorization") String authToken);
}
/*public interface InterfaceAPI {
@GET("v1/users/list?limit=10&page=0")
Call<String> checkLogin(@Header("Authorization") String authToken);
}*/