package com.airosoft.task.data.database.cursor;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.airosoft.task.data.database.DbSchema.PlaceTable;
import com.airosoft.task.model.local.PlaceModel;

public class PlaceCursorWrapper extends CursorWrapper {

    public PlaceCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public PlaceModel getPlace() {
        long id = getLong(getColumnIndex(PlaceTable.Column.ID));
        String name = getString(getColumnIndex(PlaceTable.Column.NAME));
        String countryCode = getString(getColumnIndex(PlaceTable.Column.COUNTRY_CODE));
        double latitude = getDouble(getColumnIndex(PlaceTable.Column.LATITUDE));
        double longitude = getDouble(getColumnIndex(PlaceTable.Column.LONGITUDE));

        PlaceModel place = new PlaceModel();
        place.setId(id);
        place.setName(name);
        place.setCountryCode(countryCode);
        place.setLatitude(latitude);
        place.setLongitude(longitude);

        return place;
    }
}
