package com.airosoft.task.presentation.view.weather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airosoft.task.AppConst;
import com.airosoft.task.R;
import com.airosoft.task.model.local.WeatherModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindDimen;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.date_request_text_view) TextView dateRequestTextView;
    @BindView(R.id.condition_name_text_view) TextView conditionNameTextView;
    @BindView(R.id.condition_descr_text_view) TextView conditionDescrTextView;
    @BindView(R.id.temp_text_view) TextView tempTextView;
    @BindView(R.id.temp_min_text_view) TextView tempMinTextView;
    @BindView(R.id.temp_max_text_view) TextView tempMaxTextView;
    @BindView(R.id.humidity_text_view) TextView humidityTextView;
    @BindView(R.id.cloudiness_text_view) TextView cloudinessTextView;
    @BindView(R.id.pressure_text_view) TextView pressureTextView;
    @BindView(R.id.wind_text_view) TextView windTextView;
    @BindView(R.id.condition_icon_image_view) ImageView conditionIconImageView;

    @BindDimen(R.dimen.icon_size_large) int iconSize;
    @BindString(R.string.pattern_date_time) String patternDateTime;
    @BindString(R.string.pattern_temp) String patternTemp;
    @BindString(R.string.pattern_wind) String patternWind;
    @BindString(R.string.pattern_pressure) String patternPressure;
    @BindString(R.string.pattern_humidity) String patternHumidity;
    @BindString(R.string.pattern_cloudiness) String patternCloudiness;

    @BindString(R.string.weather_unit_temp_celsius) String unitTempCelsius;
    @BindString(R.string.weather_unit_pressure) String unitPressure;
    @BindString(R.string.weather_unit_humidity) String unitHumidity;
    @BindString(R.string.weather_unit_cloudiness) String unitCloudiness;
    @BindString(R.string.weather_unit_wind) String unitWind;

    private WeatherModel weather;

    public WeatherViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void onBind(WeatherModel weather) {
        this.weather = weather;

        SimpleDateFormat dateFormat = new SimpleDateFormat(patternDateTime, Locale.US);

        dateRequestTextView.setText(dateFormat.format(new Date(weather.getDateRequest())));
        setConditionData();
        setMainData();
        setWindData();
    }

    private void setWindData() {
        windTextView.setText(String.format(
                patternWind,
                Math.round(weather.getWind().getSpeed()),
                unitWind,
                Math.round(weather.getWind().getDegree()) + "\u00B0" //unicode of degree symbol
        ));
    }

    private void setMainData() {
        cloudinessTextView.setText(String.format(
                patternCloudiness, weather.getMainData().getCloudiness(), unitCloudiness));
        humidityTextView.setText(String.format(
                patternHumidity, weather.getMainData().getHumidity(), unitHumidity));
        pressureTextView.setText(String.format(
                patternPressure, weather.getMainData().getPressure(), unitPressure));
        setTemp(tempTextView, (int)Math.round(weather.getMainData().getTemp()));
        setTemp(tempMinTextView, (int)Math.round(weather.getMainData().getTempMin()));
        setTemp(tempMaxTextView, (int)Math.round(weather.getMainData().getTempMax()));
    }

    private void setTemp(TextView textView, int temp) {
        if (temp > 0) {
            textView.setText(String.format(patternTemp, "+", temp, unitTempCelsius));
        } else if (temp < 0) {
            textView.setText(String.format(patternTemp, "-", temp, unitTempCelsius));
        } else {
            textView.setText(String.format(patternTemp, "", temp, unitTempCelsius));
        }
    }

    private void setConditionData() {
        conditionNameTextView.setText(weather.getCondition().getName());
        conditionDescrTextView.setText(weather.getCondition().getDescription());
        Picasso.get()
                .load(AppConst.PATH_ASSETS
                        + AppConst.WEATHER_ICON_FOLDER
                        + weather.getCondition().getIconName()
                        +AppConst.WEATHER_ICON_FORMAT)
                .resize(iconSize, iconSize)
                .into(conditionIconImageView);
    }
}
