package com.airosoft.task.data.database.cursor;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.airosoft.task.data.database.DbSchema.WeatherTable;
import com.airosoft.task.model.local.ConditionModel;
import com.airosoft.task.model.local.MainDataModel;
import com.airosoft.task.model.local.WeatherModel;
import com.airosoft.task.model.local.WindModel;

public class WeatherCursorWrapper extends CursorWrapper {

    public WeatherCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public WeatherModel getWeather() {
        double temp = getDouble(getColumnIndex(WeatherTable.Column.TEMP));
        double tempMin = getDouble(getColumnIndex(WeatherTable.Column.TEMP_MIN));
        double tempMax = getDouble(getColumnIndex(WeatherTable.Column.TEMP_MAX));
        double pressure = getDouble(getColumnIndex(WeatherTable.Column.PRESSURE));
        int humidity = getInt(getColumnIndex(WeatherTable.Column.HUMIDITY));
        int cloudiness = getInt(getColumnIndex(WeatherTable.Column.CLOUDINESS));
        String conditionName = getString(getColumnIndex(WeatherTable.Column.CONDITION_NAME));
        String conditionDescr = getString(getColumnIndex(WeatherTable.Column.CONDITION_DESCR));
        String conditionIconName = getString(getColumnIndex(WeatherTable.Column.CONDITION_ICON_NAME));
        double windSpeed = getDouble(getColumnIndex(WeatherTable.Column.WIND_SPEED));
        double windDegree = getDouble(getColumnIndex(WeatherTable.Column.WIND_DEGREE));
        long dateRequest = getLong(getColumnIndex(WeatherTable.Column.DATE_REQUEST));

        ConditionModel condition = new ConditionModel();
        condition.setName(conditionName);
        condition.setDescription(conditionDescr);
        condition.setIconName(conditionIconName);

        WindModel wind = new WindModel();
        wind.setSpeed(windSpeed);
        wind.setDegree(windDegree);

        MainDataModel mainData = new MainDataModel();
        mainData.setTemp(temp);
        mainData.setTempMin(tempMin);
        mainData.setTempMax(tempMax);
        mainData.setPressure(pressure);
        mainData.setHumidity(humidity);
        mainData.setCloudiness(cloudiness);

        WeatherModel weather = new WeatherModel();
        weather.setDateRequest(dateRequest);
        weather.setCondition(condition);
        weather.setMainData(mainData);
        weather.setWind(wind);

        return weather;
    }
}
