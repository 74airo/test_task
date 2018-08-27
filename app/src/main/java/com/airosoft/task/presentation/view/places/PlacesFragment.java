package com.airosoft.task.presentation.view.places;

import android.content.Intent;
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
import com.airosoft.task.data.source.local.LocalPlaceDataSourceImpl;
import com.airosoft.task.data.source.remote.RemotePlaceDataSourceImpl;
import com.airosoft.task.domain.UseCaseHandler;
import com.airosoft.task.domain.repository.PlaceRepository;
import com.airosoft.task.model.local.PlaceModel;
import com.airosoft.task.presentation.listener.OnPlaceClickListener;
import com.airosoft.task.presentation.presenter.places.PlacesPresenter;
import com.airosoft.task.presentation.view.map.MapActivity;
import com.airosoft.task.presentation.view.places.adapter.PlacesRecyclerViewAdapter;
import com.airosoft.task.presentation.view.weather.WeatherActivity;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PlacesFragment extends MvpAppCompatFragment implements PlacesFragmentView {

    @InjectPresenter PlacesPresenter presenter;
    @BindView(R.id.add_place_button) Button addPlaceButton;
    @BindView(R.id.place_recycler_view) RecyclerView placeRecyclerView;

    private Unbinder unbinder;
    private PlacesRecyclerViewAdapter adapter;

    private OnPlaceClickListener placeClickListener = new OnPlaceClickListener() {
        @Override
        public void onPlaceClick(double latitude, double longitude) {
            Intent intent = WeatherActivity.createIntent(getActivity(), latitude, longitude);
            startActivity(intent);
        }
    };

    public static PlacesFragment getNewInstance() {
        return new PlacesFragment();
    }

    @ProvidePresenter
    public PlacesPresenter providePlacesPresenter() {
        return new PlacesPresenter(
                UseCaseHandler.getInstance(),
                new PlaceRepository(
                        new LocalPlaceDataSourceImpl(DbOpenHelper.getInstance(getActivity().getApplicationContext())),
                        new RemotePlaceDataSourceImpl(ServiceCreator.createService(OwmApi.class))
                )
        );
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_places, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initRecyclerView();
        return rootView;
    }

    private void initRecyclerView() {
        adapter = new PlacesRecyclerViewAdapter(placeClickListener);
        placeRecyclerView.addItemDecoration(
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        placeRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onLoadPlaceList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.add_place_button)
    public void onClick() {
        startActivity(new Intent(getActivity(), MapActivity.class));
    }

    @Override
    public void setPlaceList(List<PlaceModel> placeList) {
        adapter.setPlaceList(placeList);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
