package com.airosoft.task.presentation.view.weather;

import com.airosoft.task.model.local.WeatherModel;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface WeatherFragmentView extends MvpView {
    void setWeatherList(List<WeatherModel> weatherList);

    @StateStrategyType(SkipStrategy.class)
    void showToast(String message);
    @StateStrategyType(SkipStrategy.class)
    void addNewWeather(WeatherModel weather);
}
