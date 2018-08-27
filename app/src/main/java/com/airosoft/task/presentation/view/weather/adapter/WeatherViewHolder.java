package com.airosoft.task.presentation.view.weather.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airosoft.task.AppConst;
import com.airosoft.task.R;
import com.airosoft.task.model.local.WeatherModel;
import com.squareup.picasso.Picasso;

import butterknife.BindDimen;
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

    private WeatherModel weather;

    public WeatherViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void onBind(WeatherModel weather) {
        this.weather = weather;

        dateRequestTextView.setText(String.format("%d", weather.getDateRequest()));
        setConditionData();
        setMainData();
        setWindData();
    }

    private void setWindData() {
        windTextView.setText(String.format("%.1f, %.1f", weather.getWind().getSpeed(), weather.getWind().getDegree()));
    }

    private void setMainData() {
        tempTextView.setText(String.valueOf(weather.getMainData().getTemp()));
        tempMinTextView.setText(String.valueOf(weather.getMainData().getTempMin()));
        tempMaxTextView.setText(String.valueOf(weather.getMainData().getTempMax()));
        humidityTextView.setText(String.valueOf(weather.getMainData().getHumidity()));
        pressureTextView.setText(String.valueOf(weather.getMainData().getPressure()));
        cloudinessTextView.setText(String.valueOf(weather.getMainData().getCloudiness()));
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
