package com.airosoft.task.presentation.view.places;

import android.support.v4.app.Fragment;

import com.airosoft.task.presentation.view.base.ContainerActivity;

public class PlacesActivity extends ContainerActivity {
    @Override
    protected Fragment createFragment() {
        return PlacesFragment.getNewInstance();
    }
}
