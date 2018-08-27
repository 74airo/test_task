package com.airosoft.task.domain.repository;

import com.airosoft.task.data.source.local.LocalWeatherDataSource;
import com.airosoft.task.data.source.remote.RemoteWeatherDataSource;
import com.airosoft.task.model.local.WeatherModel;

public class WeatherRepository implements LocalWeatherDataSource, RemoteWeatherDataSource {
    private LocalWeatherDataSource localWeatherDataSource;
    private RemoteWeatherDataSource remoteWeatherDataSource;

    public WeatherRepository(LocalWeatherDataSource localWeatherDataSource,
                             RemoteWeatherDataSource remoteWeatherDataSource) {
        this.localWeatherDataSource = localWeatherDataSource;
        this.remoteWeatherDataSource = remoteWeatherDataSource;
    }

    @Override
    public void saveWeather(double latitude, double longitude, WeatherModel weather) {
        localWeatherDataSource.saveWeather(latitude, longitude, weather);
    }

    @Override
    public void getWeatherList(double latitude, double longitude, GetWeatherListCallback getWeatherListCallback) {
        localWeatherDataSource.getWeatherList(latitude, longitude, getWeatherListCallback);
    }

    @Override
    public void getWeather(double latitude, double longitude, GetWeatherCallback getWeatherCallback) {
        remoteWeatherDataSource.getWeather(latitude, longitude, getWeatherCallback);
    }
}
