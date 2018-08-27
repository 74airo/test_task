package com.airosoft.task.presentation.view.map;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.google.android.gms.maps.model.LatLng;

@StateStrategyType(SkipStrategy.class)
public interface MapFragmentView extends MvpView {
    void addMarker(LatLng selectedLatLng);
    void removeMarker();

    void showToast(String message);
    void finish();

}
