package com.airosoft.task.data.network;

import com.airosoft.task.data.network.api.OwmConst;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceCreator {
    private static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    private static Retrofit retrofit = new Retrofit.Builder()
            .client(client)
            .baseUrl(OwmConst.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

//    public static Retrofit retrofit() {
//        return retrofit;
//    }
}
