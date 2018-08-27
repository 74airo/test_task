package com.airosoft.task.data.source.local;

import com.airosoft.task.model.local.PlaceModel;

import java.util.List;

public interface LocalPlaceDataSource {
    void savePlace(PlaceModel place);

    void getPlaceList(GetPlaceListCallback getPlaceListCallback);

    interface GetPlaceListCallback {
        void onPlaceListReceived(List<PlaceModel> placeList);
    }
}
