package com.airosoft.task.data.network.api;

import com.airosoft.task.model.remote.ServiceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OwmApi {

    @GET("weather")
    Call<ServiceResponse> getData(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("units") String units,
            @Query("appid") String applicationId
    );
}
