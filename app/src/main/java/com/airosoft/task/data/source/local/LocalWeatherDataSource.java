package com.airosoft.task.data.source.local;

import com.airosoft.task.model.local.WeatherModel;

import java.util.List;

public interface LocalWeatherDataSource {
    void saveWeather(double latitude, double longitude, WeatherModel weather);

    void getWeatherList(double latitude, double longitude, GetWeatherListCallback getWeatherListCallback);

    interface GetWeatherListCallback {
        void onWeatherListReceived(List<WeatherModel> weatherList);
    }
}
