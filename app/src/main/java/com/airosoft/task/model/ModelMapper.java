package com.airosoft.task.model;

import com.airosoft.task.model.local.ConditionModel;
import com.airosoft.task.model.local.MainDataModel;
import com.airosoft.task.model.local.PlaceModel;
import com.airosoft.task.model.local.WeatherModel;
import com.airosoft.task.model.local.WindModel;
import com.airosoft.task.model.remote.ServiceResponse;

public class ModelMapper {
    public static PlaceModel getPlaceModel(ServiceResponse serviceResponse) {
        PlaceModel place = new PlaceModel();
        place.setId(serviceResponse.getId());
        place.setName(serviceResponse.getName());
        place.setCountryCode(serviceResponse.getSys().getCountry());
        place.setLatitude(serviceResponse.getCoord().getLat());
        place.setLongitude(serviceResponse.getCoord().getLon());

        return place;
    }

    public static WeatherModel getWeatherModel(ServiceResponse serviceResponse) {
        ConditionModel condition = new ConditionModel();
        condition.setName(serviceResponse.getWeather().get(0).getMain());
        condition.setDescription(serviceResponse.getWeather().get(0).getDescription());
        condition.setIconName(serviceResponse.getWeather().get(0).getIcon());

        WindModel wind = new WindModel();
        wind.setSpeed(serviceResponse.getWind().getSpeed());
        wind.setDegree(serviceResponse.getWind().getDeg());

        MainDataModel mainData = new MainDataModel();
        mainData.setTemp(serviceResponse.getMain().getTemp());
        mainData.setTempMin(serviceResponse.getMain().getTempMin());
        mainData.setTempMax(serviceResponse.getMain().getTempMax());
        mainData.setPressure(serviceResponse.getMain().getPressure());
        mainData.setHumidity(serviceResponse.getMain().getHumidity());
        mainData.setCloudiness(serviceResponse.getClouds().getAll());

        WeatherModel weather = new WeatherModel();
        weather.setDateRequest(System.currentTimeMillis());
        weather.setCondition(condition);
        weather.setMainData(mainData);
        weather.setWind(wind);

        return weather;
    }
}
