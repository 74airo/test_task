package com.airosoft.task.presentation.view.weather.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.airosoft.task.R;
import com.airosoft.task.model.local.WeatherModel;
import com.airosoft.task.presentation.listener.OnNewItemListener;

import java.util.ArrayList;
import java.util.List;

public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<WeatherViewHolder> {

    private List<WeatherModel> weatherList;
    private OnNewItemListener newItemListener;
    public WeatherRecyclerViewAdapter(OnNewItemListener listener) {
        newItemListener = listener;
        weatherList = new ArrayList<>();
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_weather_list, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        holder.onBind(weatherList.get(position));
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public void setWeatherList(List<WeatherModel> weatherList) {
        this.weatherList = weatherList;
        notifyDataSetChanged();
    }

    public void addWeatherItem(WeatherModel weather) {
        int addToTop = 0;
        weatherList.add(addToTop, weather);
        newItemListener.onItemAdded(addToTop);
        notifyItemInserted(addToTop);
    }
}
