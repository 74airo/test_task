package com.airosoft.task.presentation.presenter.map;

import android.location.Location;

import com.airosoft.task.domain.UseCase;
import com.airosoft.task.domain.UseCaseHandler;
import com.airosoft.task.domain.interactor.GetPlaceInteractor;
import com.airosoft.task.domain.interactor.SavePlaceInteractor;
import com.airosoft.task.domain.repository.PlaceRepository;
import com.airosoft.task.model.local.PlaceModel;
import com.airosoft.task.presentation.view.map.MapFragmentView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.google.android.gms.maps.model.LatLng;

@InjectViewState
public class MapPresenter extends MvpPresenter<MapFragmentView> {

    private UseCaseHandler useCaseHandler;
    private GetPlaceInteractor getPlaceInteractor;
    private SavePlaceInteractor savePlaceInteractor;

    private LatLng selectedLatLng;

    private UseCase.Callback<SavePlaceInteractor.ResponseData> savePlaceInteractorCallback =
            new UseCase.Callback<SavePlaceInteractor.ResponseData>() {
                @Override
                public void onSuccess(SavePlaceInteractor.ResponseData response) {
                    getViewState().finish();
                }

                @Override
                public void onError() {
                    getViewState().showToast("savePlace onError");
                }
            };

    private UseCase.Callback<GetPlaceInteractor.ResponseData> getPlaceInteractorCallback =
            new UseCase.Callback<GetPlaceInteractor.ResponseData>() {
                @Override
                public void onSuccess(GetPlaceInteractor.ResponseData response) {
                    PlaceModel place = response.getPlace();
                    SavePlaceInteractor.RequestData request =
                            new SavePlaceInteractor.RequestData(place);
                    useCaseHandler.execute(savePlaceInteractor, request, savePlaceInteractorCallback);
                }

                @Override
                public void onError() {
                    getViewState().showToast("getPlace onError");
                }
            };

    public MapPresenter(UseCaseHandler useCaseHandler,
                        PlaceRepository placeRepository) {
        this.useCaseHandler = useCaseHandler;
        getPlaceInteractor = new GetPlaceInteractor(placeRepository);
        savePlaceInteractor = new SavePlaceInteractor(placeRepository);
    }

    public void onMapReady() {
        if (selectedLatLng != null) {
            getViewState().addMarker(selectedLatLng);
        } else {
            getViewState().removeMarker();
        }
    }

    public void onMapClick(LatLng latLng) {
        selectedLatLng = latLng;
        getViewState().removeMarker();
        getViewState().addMarker(selectedLatLng);
    }

    public void onCancelSelection() {
        selectedLatLng = null;
        getViewState().removeMarker();
    }

    public void onConfirmSelection() {
        GetPlaceInteractor.RequestData request =
                new GetPlaceInteractor.RequestData(selectedLatLng.latitude, selectedLatLng.longitude);
        useCaseHandler.execute(getPlaceInteractor, request, getPlaceInteractorCallback);
    }

    public void onMyLocationReceived(Location location) {
        selectedLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        getViewState().addMarker(selectedLatLng);
    }
}
