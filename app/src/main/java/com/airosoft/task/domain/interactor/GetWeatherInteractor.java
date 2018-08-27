package com.airosoft.task.domain.interactor;

import com.airosoft.task.data.source.remote.RemoteWeatherDataSource;
import com.airosoft.task.domain.UseCase;
import com.airosoft.task.domain.repository.WeatherRepository;
import com.airosoft.task.model.local.WeatherModel;

public class GetWeatherInteractor extends UseCase<GetWeatherInteractor.RequestData, GetWeatherInteractor.ResponseData>{
    private WeatherRepository weatherRepository;

    public GetWeatherInteractor(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Override
    public void executeUseCase(RequestData requestData) {
        double latitude = requestData.getLatitude();
        double longitude = requestData.getLongitude();
        weatherRepository.getWeather(latitude, longitude,
                new RemoteWeatherDataSource.GetWeatherCallback() {
            @Override
            public void onWeatherReceived(WeatherModel weather) {
                ResponseData response = new ResponseData(weather);
                getUseCaseCallback().onSuccess(response);
            }

            @Override
            public void onReceivingError() {
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestData implements UseCase.RequestData {
        private double latitude;
        private double longitude;

        public RequestData(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
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
