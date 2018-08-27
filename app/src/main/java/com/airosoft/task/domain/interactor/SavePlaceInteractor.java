package com.airosoft.task.domain.interactor;

import com.airosoft.task.domain.UseCase;
import com.airosoft.task.domain.repository.PlaceRepository;
import com.airosoft.task.model.local.PlaceModel;

public class SavePlaceInteractor extends UseCase<SavePlaceInteractor.RequestData, SavePlaceInteractor.ResponseData>{

    private PlaceRepository placeRepository;

    public SavePlaceInteractor(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    @Override
    public void executeUseCase(RequestData requestData) {
        PlaceModel placeModel = requestData.getPlace();
        placeRepository.savePlace(placeModel);

        getUseCaseCallback().onSuccess(null);
    }

    public static final class RequestData implements UseCase.RequestData {
        private PlaceModel place;

        public RequestData(PlaceModel place) {
            this.place = place;
        }

        public PlaceModel getPlace() {
            return place;
        }
    }

    public static final class ResponseData implements UseCase.ResponseData {
        // FIXME: 26.08.2018
    }
}
