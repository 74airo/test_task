package com.airosoft.task.data.database;

public class DbSchema {
    public static final class WeatherTable {
        public static final String NAME = "weather_table";
        public static final class Column {
            public static final String TEMP = "temperature";
            public static final String TEMP_MIN = "temp_min";
            public static final String TEMP_MAX = "temp_max";
            public static final String PRESSURE = "pressure";
            public static final String HUMIDITY = "humidity";
            public static final String CLOUDINESS = "cloudiness";
            public static final String CONDITION_NAME = "condition_name";
            public static final String CONDITION_DESCR = "condition_descr";
            public static final String CONDITION_ICON_NAME = "condition_icon_name";
            public static final String WIND_SPEED = "wind_speed";
            public static final String WIND_DEGREE = "wind_degree";
            public static final String DATE_REQUEST = "date_request";

            /*
            * Fields for foreign keys
            */
            public static final String PLACE_LAT = "place_latitude";
            public static final String PLACE_LON = "place_longitude";
        }
    }

    public static final class PlaceTable {
        public static final String NAME = "place_table";
        public static final class Column {
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String COUNTRY_CODE = "country_code";
            public static final String LATITUDE = "latitude";
            public static final String LONGITUDE = "longitude";
        }
    }
}
