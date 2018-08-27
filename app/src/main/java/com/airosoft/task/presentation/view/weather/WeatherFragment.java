package com.airosoft.task.presentation.view.weather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.airosoft.task.R;
import com.airosoft.task.data.database.DbOpenHelper;
import com.airosoft.task.data.network.ServiceCreator;
import com.airosoft.task.data.network.api.OwmApi;
import com.airosoft.task.data.source.local.LocalWeatherDataSourceImpl;
import com.airosoft.task.data.source.remote.RemoteWeatherDataSourceImpl;
import com.airosoft.task.domain.UseCaseHandler;
import com.airosoft.task.domain.interactor.GetWeatherInteractor;
import com.airosoft.task.domain.repository.WeatherRepository;
import com.airosoft.task.model.local.WeatherModel;
import com.airosoft.task.presentation.listener.OnNewItemListener;
import com.airosoft.task.presentation.presenter.weather.WeatherPresenter;
import com.airosoft.task.presentation.view.weather.adapter.WeatherRecyclerViewAdapter;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class WeatherFragment extends MvpAppCompatFragment implements WeatherFragmentView {
    private static final String ARG_PLACE_LAT =
            "com.airosoft.task.presentation.view.weather.weather_fragment.arg_place_lat";
    private static final String ARG_PLACE_LON =
            "com.airosoft.task.presentation.view.weather.weather_fragment.arg_place_lon";

    @InjectPresenter WeatherPresenter presenter;
    @BindView(R.id.check_weather_button) Button checkWeatherButton;
    @BindView(R.id.weather_recycler_view) RecyclerView weatherRecyclerView;

    private Unbinder unbinder;
    private WeatherRecyclerViewAdapter adapter;
    private OnNewItemListener newItemListener = new OnNewItemListener() {
        @Override
        public void onItemAdded(int position) {
            weatherRecyclerView.scrollToPosition(position);
        }
    };

    public static WeatherFragment getNewInstance(double latitude, double longitude) {
        Bundle args = new Bundle();
        args.putDouble(ARG_PLACE_LAT, latitude);
        args.putDouble(ARG_PLACE_LON, longitude);

        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @ProvidePresenter
    public WeatherPresenter provideWeatherPresenter() {
        return new WeatherPresenter(
                UseCaseHandler.getInstance(),
                new WeatherRepository(
                        new LocalWeatherDataSourceImpl(DbOpenHelper.getInstance(getActivity().getApplicationContext())),
                        new RemoteWeatherDataSourceImpl(ServiceCreator.createService(OwmApi.class))
                )
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initRecyclerView();
        return rootView;
    }

    private void initRecyclerView() {
        adapter = new WeatherRecyclerViewAdapter(newItemListener);
        weatherRecyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        weatherRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume(
                getArguments().getDouble(ARG_PLACE_LAT), getArguments().getDouble(ARG_PLACE_LON));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.check_weather_button)
    public void onClick() {
        presenter.onCheckWeather();
    }

    @Override
    public void setWeatherList(List<WeatherModel> weatherList) {
        adapter.setWeatherList(weatherList);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addNewWeather(WeatherModel weather) {
        adapter.addWeatherItem(weather);
    }
}
