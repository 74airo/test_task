package com.airosoft.task.data.source.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.airosoft.task.data.database.DbOpenHelper;
import com.airosoft.task.data.database.DbSchema;
import com.airosoft.task.data.database.DbSchema.WeatherTable;
import com.airosoft.task.data.database.cursor.WeatherCursorWrapper;
import com.airosoft.task.model.local.WeatherModel;

import java.util.ArrayList;
import java.util.List;

public class LocalWeatherDataSourceImpl implements LocalWeatherDataSource {

    private SQLiteDatabase sqliteDatabase;

    public LocalWeatherDataSourceImpl(DbOpenHelper dbOpenHelper) {
        sqliteDatabase = dbOpenHelper.getWritableDatabase();
    }

    @Override
    public void saveWeather(double latitude, double longitude, WeatherModel weather) {
        sqliteDatabase.insert(
                WeatherTable.NAME, null, getValues(latitude, longitude, weather));
    }

    @Override
    public void getWeatherList(double latitude, double longitude, GetWeatherListCallback getWeatherListCallback) {
        List<WeatherModel> weatherList = new ArrayList<>();

        WeatherCursorWrapper cursor = query(
                WeatherTable.Column.PLACE_LAT + " = ?" + " AND " + WeatherTable.Column.PLACE_LON + " = ?",
                new String[]{String.valueOf(latitude), String.valueOf(longitude)}
        );

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                weatherList.add(cursor.getWeather());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        getWeatherListCallback.onWeatherListReceived(weatherList);
    }

    private ContentValues getValues(double latitude, double longitude, WeatherModel weather) {
        ContentValues values = new ContentValues();
        values.put(WeatherTable.Column.DATE_REQUEST, weather.getDateRequest());

        values.put(WeatherTable.Column.TEMP, weather.getMainData().getTemp());
        values.put(WeatherTable.Column.TEMP_MIN, weather.getMainData().getTempMin());
        values.put(WeatherTable.Column.TEMP_MAX, weather.getMainData().getTempMax());
        values.put(WeatherTable.Column.PRESSURE, weather.getMainData().getPressure());
        values.put(WeatherTable.Column.HUMIDITY, weather.getMainData().getHumidity());
        values.put(WeatherTable.Column.CLOUDINESS, weather.getMainData().getCloudiness());

        values.put(WeatherTable.Column.CONDITION_NAME, weather.getCondition().getName());
        values.put(WeatherTable.Column.CONDITION_DESCR, weather.getCondition().getDescription());
        values.put(WeatherTable.Column.CONDITION_ICON_NAME, weather.getCondition().getIconName());

        values.put(WeatherTable.Column.WIND_SPEED, weather.getWind().getSpeed());
        values.put(WeatherTable.Column.WIND_DEGREE, weather.getWind().getDegree());

        values.put(WeatherTable.Column.PLACE_LAT, latitude);
        values.put(WeatherTable.Column.PLACE_LON, longitude);

        return values;
    }

    private WeatherCursorWrapper query(String where, String[] args) {
        Cursor cursor = sqliteDatabase.query(
                WeatherTable.NAME,
                null,
                where,
                args,
                null,
                null,
                null
        );

        return new WeatherCursorWrapper(cursor);
    }
}
