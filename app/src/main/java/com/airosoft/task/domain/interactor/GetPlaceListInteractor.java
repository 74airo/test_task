package com.airosoft.task.domain.interactor;

import com.airosoft.task.data.source.local.LocalPlaceDataSource;
import com.airosoft.task.domain.UseCase;
import com.airosoft.task.domain.repository.PlaceRepository;
import com.airosoft.task.model.local.PlaceModel;

import java.util.List;

public class GetPlaceListInteractor extends UseCase<GetPlaceListInteractor.RequestData, GetPlaceListInteractor.ResponseData>{
    private PlaceRepository placeRepository;

    public GetPlaceListInteractor(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    public void executeUseCase(RequestData requestData) {
        placeRepository.getPlaceList(new LocalPlaceDataSource.GetPlaceListCallback() {
            @Override
            public void onPlaceListReceived(List<PlaceModel> placeList) {
                ResponseData response = new ResponseData(placeList);
                getUseCaseCallback().onSuccess(response);
            }
        });
    }

    public static final class RequestData implements UseCase.RequestData {
//        Must be empty;
    }

    public static final class ResponseData implements UseCase.ResponseData {
        private List<PlaceModel> placeList;

        public ResponseData(List<PlaceModel> placeList) {
            this.placeList = placeList;
        }

        public List<PlaceModel> getPlaceList() {
            return placeList;
        }
    }
}
