package com.airosoft.task.presentation.view.places;

import com.airosoft.task.model.local.PlaceModel;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface PlacesFragmentView extends MvpView {
    void setPlaceList(List<PlaceModel> placeList);

    @StateStrategyType(SkipStrategy.class)
    void showToast(String message);
}
