package com.airosoft.task.domain.repository;

import com.airosoft.task.data.source.local.LocalPlaceDataSource;
import com.airosoft.task.data.source.remote.RemotePlaceDataSource;
import com.airosoft.task.model.local.PlaceModel;

public class PlaceRepository implements LocalPlaceDataSource, RemotePlaceDataSource {
    private LocalPlaceDataSource localPlaceDataSource;
    private RemotePlaceDataSource remotePlaceDataSource;

    public PlaceRepository(LocalPlaceDataSource localPlaceDataSource,
                           RemotePlaceDataSource remotePlaceDataSource) {
        this.localPlaceDataSource = localPlaceDataSource;
        this.remotePlaceDataSource = remotePlaceDataSource;
    }

    @Override
    public void savePlace(PlaceModel place) {
        localPlaceDataSource.savePlace(place);
    }

    @Override
    public void getPlaceList(GetPlaceListCallback getPlaceListCallback) {
        localPlaceDataSource.getPlaceList(getPlaceListCallback);
    }

    @Override
    public void getPlace(double latitude, double longitude, GetPlaceCallback getPlaceCallback) {
        remotePlaceDataSource.getPlace(latitude, longitude, getPlaceCallback);
    }
}
