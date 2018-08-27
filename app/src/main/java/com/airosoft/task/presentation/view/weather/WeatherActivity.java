package com.airosoft.task.presentation.view.weather;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.airosoft.task.presentation.view.base.ContainerActivity;

public class WeatherActivity extends ContainerActivity {
    private static final String EXTRA_PLACE_LAT =
            "com.airosoft.task.presentation.view.weather.weather_activity.extra_place_lat";
    private static final String EXTRA_PLACE_LON =
            "com.airosoft.task.presentation.view.weather.weather_activity.extra_place_lon";

    @Override
    protected Fragment createFragment() {
        double latitude = getIntent().getDoubleExtra(EXTRA_PLACE_LAT, 0);
        double longitude = getIntent().getDoubleExtra(EXTRA_PLACE_LON, 0);

        return WeatherFragment.getNewInstance(latitude, longitude);
    }

    public static Intent createIntent(Context context, double latitude, double longitude) {
        Intent intent = new Intent(context, WeatherActivity.class);
        intent.putExtra(EXTRA_PLACE_LAT, latitude);
        intent.putExtra(EXTRA_PLACE_LON, longitude);
        return intent;
    }
}
