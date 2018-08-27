package com.airosoft.task.data.source.remote;

import com.airosoft.task.model.local.WeatherModel;

public interface RemoteWeatherDataSource {
    void getWeather(double latitude, double longitude, GetWeatherCallback getWeatherCallback);

    interface GetWeatherCallback {
        void onWeatherReceived(WeatherModel weather);
        void onReceivingError();
    }
}
