package com.airosoft.task.presentation.view.map;

import android.support.v4.app.Fragment;

import com.airosoft.task.presentation.view.base.ContainerActivity;

public class MapActivity extends ContainerActivity {
    @Override
    protected Fragment createFragment() {
        return MapFragment.getNewInstance();
    }
}
