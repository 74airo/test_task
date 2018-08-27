package com.airosoft.task.presentation.presenter.weather;

import com.airosoft.task.domain.UseCase;
import com.airosoft.task.domain.UseCaseHandler;
import com.airosoft.task.domain.interactor.GetWeatherInteractor;
import com.airosoft.task.domain.interactor.GetWeatherListInteractor;
import com.airosoft.task.domain.interactor.SaveWeatherInteractor;
import com.airosoft.task.domain.repository.WeatherRepository;
import com.airosoft.task.model.local.WeatherModel;
import com.airosoft.task.presentation.view.weather.WeatherFragmentView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

@InjectViewState
public class WeatherPresenter extends MvpPresenter<WeatherFragmentView> {
    private UseCaseHandler useCaseHandler;

    private GetWeatherInteractor getWeatherInteractor;
    private GetWeatherListInteractor getWeatherListInteractor;
    private SaveWeatherInteractor saveWeatherInteractor;

    private double latitude;
    private double longitude;

    private UseCase.Callback<SaveWeatherInteractor.ResponseData> saveWeatherInteractorCallback =
            new UseCase.Callback<SaveWeatherInteractor.ResponseData>() {
                @Override
                public void onSuccess(SaveWeatherInteractor.ResponseData response) {
                    WeatherModel weather = response.getWeather();
                    getViewState().addNewWeather(weather);
                }

                @Override
                public void onError() {
                    getViewState().showToast("saveWeather onError");
                }
            };

    private UseCase.Callback<GetWeatherInteractor.ResponseData> getWeatherInteractorCallback =
            new UseCase.Callback<GetWeatherInteractor.ResponseData>() {
                @Override
                public void onSuccess(GetWeatherInteractor.ResponseData response) {
                    WeatherModel weather = response.getWeather();

                    SaveWeatherInteractor.RequestData request =
                            new SaveWeatherInteractor.RequestData(latitude, longitude, weather);
                    useCaseHandler.execute(saveWeatherInteractor, request, saveWeatherInteractorCallback);
                }

                @Override
                public void onError() {
                    getViewState().showToast("getWeather onError");
                }
            };

    private UseCase.Callback<GetWeatherListInteractor.ResponseData> getWeatherListInteractorCallback =
            new UseCase.Callback<GetWeatherListInteractor.ResponseData>() {
                @Override
                public void onSuccess(GetWeatherListInteractor.ResponseData response) {
                    List<WeatherModel> weatherList = response.getWeatherList();
                    getViewState().setWeatherList(weatherList);
                }

                @Override
                public void onError() {
                    getViewState().showToast("getWeatherList onError");
                }
            };

    public WeatherPresenter(UseCaseHandler useCaseHandler,
                            WeatherRepository weatherRepository) {
        this.useCaseHandler = useCaseHandler;

        getWeatherInteractor = new GetWeatherInteractor(weatherRepository);
        getWeatherListInteractor = new GetWeatherListInteractor(weatherRepository);
        saveWeatherInteractor = new SaveWeatherInteractor(weatherRepository);

    }

    public void onResume(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;

        GetWeatherListInteractor.RequestData request =
                new GetWeatherListInteractor.RequestData(latitude, longitude);
        useCaseHandler.execute(getWeatherListInteractor, request, getWeatherListInteractorCallback);
    }

    public void onCheckWeather() {
        GetWeatherInteractor.RequestData request =
                new GetWeatherInteractor.RequestData(latitude, longitude);

        useCaseHandler.execute(getWeatherInteractor, request, getWeatherInteractorCallback);
    }
}
