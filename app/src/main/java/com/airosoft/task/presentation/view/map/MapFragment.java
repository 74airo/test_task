package com.airosoft.task.presentation.view.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airosoft.task.R;
import com.airosoft.task.data.database.DbOpenHelper;
import com.airosoft.task.data.network.ServiceCreator;
import com.airosoft.task.data.network.api.OwmApi;
import com.airosoft.task.data.source.local.LocalPlaceDataSourceImpl;
import com.airosoft.task.data.source.remote.RemotePlaceDataSourceImpl;
import com.airosoft.task.domain.UseCaseHandler;
import com.airosoft.task.domain.repository.PlaceRepository;
import com.airosoft.task.presentation.presenter.map.MapPresenter;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MapFragment extends MvpAppCompatFragment implements MapFragmentView, OnMapReadyCallback, GoogleMap.OnMapClickListener, OnSuccessListener<Location> {
    private static final int REQUEST_LOCATION_PERMISSION = 0;

    @InjectPresenter MapPresenter presenter;
    @BindView(R.id.google_map_view) MapView mapView;

    private Unbinder unbinder;
    private GoogleMap googleMap;
    private FusedLocationProviderClient locationProviderClient;

    public static MapFragment getNewInstance() {
        return new MapFragment();
    }

    @ProvidePresenter
    public MapPresenter provideMapPresenter() {
        return new MapPresenter(
                UseCaseHandler.getInstance(),
                new PlaceRepository(
                        new LocalPlaceDataSourceImpl(DbOpenHelper.getInstance(getActivity().getApplicationContext())),
                        new RemotePlaceDataSourceImpl(ServiceCreator.createService(OwmApi.class))
                )
        );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        locationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_map, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_ok:
                presenter.onConfirmSelection();
                return true;
            case R.id.menu_item_cancel:
                presenter.onCancelSelection();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        MapsInitializer.initialize(getActivity());

        googleMap = map;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setOnMapClickListener(this);

        if (checkLocationPermission()) {
            locationProviderClient.getLastLocation().addOnSuccessListener(this);
        } else {
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }

        presenter.onMapReady();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (checkLocationPermission()) {
                    locationProviderClient.getLastLocation().addOnSuccessListener(this);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        presenter.onMapClick(latLng);
    }

    @Override
    public void addMarker(LatLng selectedLatLng) {
        googleMap.addMarker(new MarkerOptions().position(selectedLatLng));
        googleMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(selectedLatLng, googleMap.getCameraPosition().zoom));
        setMenuVisibility(true);
    }

    @Override
    public void removeMarker() {
        googleMap.clear();
        setMenuVisibility(false);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    private boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onSuccess(Location location) {
        presenter.onMyLocationReceived(location);
    }
}
