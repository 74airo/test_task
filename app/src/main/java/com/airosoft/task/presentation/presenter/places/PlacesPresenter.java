package com.airosoft.task.presentation.presenter.places;

import com.airosoft.task.domain.UseCase;
import com.airosoft.task.domain.UseCaseHandler;
import com.airosoft.task.domain.interactor.GetPlaceListInteractor;
import com.airosoft.task.domain.repository.PlaceRepository;
import com.airosoft.task.model.local.PlaceModel;
import com.airosoft.task.presentation.view.places.PlacesFragmentView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

@InjectViewState
public class PlacesPresenter extends MvpPresenter<PlacesFragmentView> {
    private UseCaseHandler useCaseHandler;
    private GetPlaceListInteractor getPlaceListInteractor;

    private UseCase.Callback<GetPlaceListInteractor.ResponseData> getPlaceListInteractorCallback =
            new UseCase.Callback<GetPlaceListInteractor.ResponseData>() {
                @Override
                public void onSuccess(GetPlaceListInteractor.ResponseData response) {
                    List<PlaceModel> placeList = response.getPlaceList();
                    getViewState().setPlaceList(placeList);
                }

                @Override
                public void onError() {
                    getViewState().showToast("getPlaceList onError");
                }
            };

    public PlacesPresenter(UseCaseHandler useCaseHandler,
                           PlaceRepository placeRepository) {
        this.useCaseHandler = useCaseHandler;
        getPlaceListInteractor = new GetPlaceListInteractor(placeRepository);
    }

    public void onLoadPlaceList() {
        useCaseHandler.execute(
                getPlaceListInteractor,
                new GetPlaceListInteractor.RequestData(),
                getPlaceListInteractorCallback);
    }
}
