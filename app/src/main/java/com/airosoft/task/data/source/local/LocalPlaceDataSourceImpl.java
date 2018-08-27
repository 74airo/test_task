package com.airosoft.task.data.source.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.airosoft.task.data.database.DbOpenHelper;
import com.airosoft.task.data.database.DbSchema;
import com.airosoft.task.data.database.DbSchema.PlaceTable;
import com.airosoft.task.data.database.cursor.PlaceCursorWrapper;
import com.airosoft.task.model.local.PlaceModel;
import com.airosoft.task.model.local.WeatherModel;

import java.util.ArrayList;
import java.util.List;

public class LocalPlaceDataSourceImpl implements LocalPlaceDataSource {

    private SQLiteDatabase sqliteDatabase;

    public LocalPlaceDataSourceImpl(DbOpenHelper openHelper) {
        sqliteDatabase = openHelper.getWritableDatabase();
    }

    @Override
    public void savePlace(PlaceModel place) {
        sqliteDatabase.insert(PlaceTable.NAME, null, getValues(place));
    }

    @Override
    public void getPlaceList(GetPlaceListCallback getPlaceListCallback) {
        List<PlaceModel> placeList = new ArrayList<>();

        PlaceCursorWrapper cursor = query(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                placeList.add(cursor.getPlace());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        getPlaceListCallback.onPlaceListReceived(placeList);

    }

    private ContentValues getValues(PlaceModel place) {
        ContentValues values = new ContentValues();
        values.put(PlaceTable.Column.ID, place.getId());
        values.put(PlaceTable.Column.NAME, place.getName());
        values.put(PlaceTable.Column.COUNTRY_CODE, place.getCountryCode());
        values.put(PlaceTable.Column.LATITUDE, place.getLatitude());
        values.put(PlaceTable.Column.LONGITUDE, place.getLongitude());

        return values;
    }

    private PlaceCursorWrapper query(String where, String[] args) {
        Cursor cursor = sqliteDatabase.query(
                PlaceTable.NAME,
                null,
                where,
                args,
                null,
                null,
                null
        );

        return new PlaceCursorWrapper(cursor);
    }
}
