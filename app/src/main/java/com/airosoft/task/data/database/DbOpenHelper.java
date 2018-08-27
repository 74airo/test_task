package com.airosoft.task.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.airosoft.task.data.database.DbSchema.PlaceTable;
import com.airosoft.task.data.database.DbSchema.WeatherTable;

public class DbOpenHelper extends SQLiteOpenHelper {
    private static DbOpenHelper instance;

    private static final String DATABASE_NAME = "test_task.sqlite";
    private static final int DATABASE_VERSION = 1;

    private DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public synchronized static DbOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbOpenHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("pragma foreign_keys=on");
        db.execSQL(
                "create table " + PlaceTable.NAME + "("
                + PlaceTable.Column.ID + " integer primary key, "
                + PlaceTable.Column.NAME + " text not null, "
                + PlaceTable.Column.COUNTRY_CODE + " text not null, "
                + PlaceTable.Column.LATITUDE + " double not null, "
                + PlaceTable.Column.LONGITUDE + " double not null);"
        );
        db.execSQL(
                "create table " + WeatherTable.NAME + "("
                + WeatherTable.Column.TEMP + " double not null, "
                + WeatherTable.Column.TEMP_MIN + " double not null, "
                + WeatherTable.Column.TEMP_MAX + " double not null, "
                + WeatherTable.Column.PRESSURE + " double not null, "
                + WeatherTable.Column.HUMIDITY + " integer not null, "
                + WeatherTable.Column.CLOUDINESS + " integer not null, "
                + WeatherTable.Column.CONDITION_NAME + " text not null, "
                + WeatherTable.Column.CONDITION_DESCR + " text not null, "
                + WeatherTable.Column.CONDITION_ICON_NAME + " text not null, "
                + WeatherTable.Column.WIND_SPEED + " double not null, "
                + WeatherTable.Column.WIND_DEGREE + " double not null, "
                + WeatherTable.Column.DATE_REQUEST + " integer not null, "
                + WeatherTable.Column.PLACE_LAT + " double not null, "
                + WeatherTable.Column.PLACE_LON + " double not null, "
                + "foreign key ("
                        + WeatherTable.Column.PLACE_LAT + ", " + WeatherTable.Column.PLACE_LON + ")"
                + " references " + PlaceTable.NAME + "("
                        + PlaceTable.Column.LATITUDE + ", " + PlaceTable.Column.LONGITUDE + "));"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
