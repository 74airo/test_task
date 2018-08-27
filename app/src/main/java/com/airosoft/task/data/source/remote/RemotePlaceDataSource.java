package com.airosoft.task.data.source.remote;

import com.airosoft.task.model.local.PlaceModel;

public interface RemotePlaceDataSource {

    void getPlace(double latitude, double longitude, GetPlaceCallback getPlaceCallback);

    interface GetPlaceCallback {
        void onPlaceReceived(PlaceModel place);
        void onReceivingError();
    }
}
