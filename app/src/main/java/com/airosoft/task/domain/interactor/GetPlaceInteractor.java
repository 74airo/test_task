package com.airosoft.task.domain.interactor;

import com.airosoft.task.data.source.remote.RemotePlaceDataSource;
import com.airosoft.task.domain.UseCase;
import com.airosoft.task.domain.repository.PlaceRepository;
import com.airosoft.task.model.local.PlaceModel;

public class GetPlaceInteractor extends UseCase<GetPlaceInteractor.RequestData, GetPlaceInteractor.ResponseData> {

    private PlaceRepository placeRepository;

    public GetPlaceInteractor(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    public void executeUseCase(RequestData requestData) {
        double latitude = requestData.getLatitude();
        double longitude = requestData.getLongitude();
        placeRepository.getPlace(latitude, longitude, new RemotePlaceDataSource.GetPlaceCallback() {
            @Override
            public void onPlaceReceived(PlaceModel place) {
                ResponseData response = new ResponseData(place);
                getUseCaseCallback().onSuccess(response);
            }

            @Override
            public void onReceivingError() {
                getUseCaseCallback().onError();
            }
        });
    }

    public static final class RequestData implements UseCase.RequestData {
        private double latitude;
        private double longitude;

        public RequestData(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }

    public static final class ResponseData implements UseCase.ResponseData {
        private PlaceModel place;

        public ResponseData(PlaceModel place) {
            this.place = place;
        }

        public PlaceModel getPlace() {
            return place;
        }
    }
}
