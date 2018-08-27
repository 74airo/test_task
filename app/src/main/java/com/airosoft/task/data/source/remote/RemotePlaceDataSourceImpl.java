package com.airosoft.task.data.source.remote;

import com.airosoft.task.data.network.api.OwmApi;
import com.airosoft.task.data.network.api.OwmConst;
import com.airosoft.task.model.ModelMapper;
import com.airosoft.task.model.local.PlaceModel;
import com.airosoft.task.model.remote.ServiceResponse;

import java.io.IOException;

import retrofit2.Response;

public class RemotePlaceDataSourceImpl implements RemotePlaceDataSource {

    private OwmApi client;

    public RemotePlaceDataSourceImpl(OwmApi client) {
        this.client = client;
    }

    @Override
    public void getPlace(double latitude, double longitude, GetPlaceCallback getPlaceCallback) {
        try {
            Response<ServiceResponse> response =
                    client.getData(latitude, longitude, OwmConst.UNITS, OwmConst.APP_ID).execute();
            if (response.isSuccessful()) {
                PlaceModel place = ModelMapper.getPlaceModel(response.body());
                getPlaceCallback.onPlaceReceived(place);
            } else {
                getPlaceCallback.onReceivingError();
            }
        } catch (IOException e) {
            getPlaceCallback.onReceivingError();
        }
    }
}
