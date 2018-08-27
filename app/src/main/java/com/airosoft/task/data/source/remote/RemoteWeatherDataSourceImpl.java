package com.airosoft.task.data.source.remote;

import com.airosoft.task.data.network.api.OwmApi;
import com.airosoft.task.data.network.api.OwmConst;
import com.airosoft.task.model.ModelMapper;
import com.airosoft.task.model.local.WeatherModel;
import com.airosoft.task.model.remote.ServiceResponse;

import java.io.IOException;

import retrofit2.Response;

public class RemoteWeatherDataSourceImpl implements RemoteWeatherDataSource {

    private OwmApi client;

    public RemoteWeatherDataSourceImpl(OwmApi client) {
        this.client = client;
    }

    @Override
    public void getWeather(double latitude, double longitude, GetWeatherCallback getWeatherCallback) {
        try {
            Response<ServiceResponse> response =
                    client.getData(latitude, longitude, OwmConst.UNITS, OwmConst.APP_ID).execute();
            if (response.isSuccessful()) {
                WeatherModel weather = ModelMapper.getWeatherModel(response.body());
                getWeatherCallback.onWeatherReceived(weather);
            } else {
                getWeatherCallback.onReceivingError();
            }
        } catch (IOException e) {
            getWeatherCallback.onReceivingError();
        }
    }
}
