package com.airosoft.task.domain.interactor;

import com.airosoft.task.domain.UseCase;
import com.airosoft.task.domain.repository.WeatherRepository;
import com.airosoft.task.model.local.WeatherModel;

public class SaveWeatherInteractor extends UseCase<SaveWeatherInteractor.RequestData, SaveWeatherInteractor.ResponseData> {
    private WeatherRepository weatherRepository;

    public SaveWeatherInteractor(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Override
    public void executeUseCase(SaveWeatherInteractor.RequestData requestData) {
        double latitude = requestData.getLatitude();
        double longitude = requestData.getLongitude();
        WeatherModel weather = requestData.getWeather();
        weatherRepository.saveWeather(latitude, longitude, weather);

        getUseCaseCallback().onSuccess(new ResponseData(weather));
    }

    public static final class RequestData implements UseCase.RequestData {
        private double latitude;
        private double longitude;
        private WeatherModel weather;

        public RequestData(double latitude, double longitude, WeatherModel weather) {
            this.latitude = latitude;
            this.longitude = longitude;
            this.weather = weather;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public WeatherModel getWeather() {
            return weather;
        }
    }

    public static final class ResponseData implements UseCase.ResponseData {
        private WeatherModel weather;

        public ResponseData(WeatherModel weather) {
            this.weather = weather;
        }

        public WeatherModel getWeather() {
            return weather;
        }
    }
}
