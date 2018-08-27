package com.airosoft.task.domain.interactor;

import com.airosoft.task.data.source.local.LocalWeatherDataSource;
import com.airosoft.task.domain.UseCase;
import com.airosoft.task.domain.repository.WeatherRepository;
import com.airosoft.task.model.local.WeatherModel;

import java.util.Collections;
import java.util.List;

public class GetWeatherListInteractor extends UseCase<GetWeatherListInteractor.RequestData, GetWeatherListInteractor.ResponseData> {

    private WeatherRepository weatherRepository;

    public GetWeatherListInteractor(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    @Override
    public void executeUseCase(RequestData requestData) {
        double latitude = requestData.getLatitude();
        double longitude = requestData.getLongitude();
        weatherRepository.getWeatherList(latitude, longitude, new LocalWeatherDataSource.GetWeatherListCallback() {
            @Override
            public void onWeatherListReceived(List<WeatherModel> weatherList) {
                Collections.reverse(weatherList);
                ResponseData response = new ResponseData(weatherList);
                getUseCaseCallback().onSuccess(response);
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
        private List<WeatherModel> weatherList;

        public ResponseData(List<WeatherModel> weatherList) {
            this.weatherList = weatherList;
        }

        public List<WeatherModel> getWeatherList() {
            return weatherList;
        }
    }
}
