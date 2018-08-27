package com.airosoft.task.presentation.view.places.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.airosoft.task.R;
import com.airosoft.task.model.local.PlaceModel;
import com.airosoft.task.presentation.listener.OnPlaceClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlacesViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.item_place_list_layout) ConstraintLayout itemLayout;
    @BindView(R.id.place_full_name_text_view) TextView placeFullNameTextView;
    private OnPlaceClickListener placeClickListener;
    private PlaceModel place;

    public PlacesViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void onBind(OnPlaceClickListener placeClickListener, PlaceModel place) {
        this.placeClickListener = placeClickListener;
        this.place = place;

        placeFullNameTextView.setText(String.format("%s, %s", place.getName(), place.getCountryCode() ));
    }

    @OnClick(R.id.item_place_list_layout)
    public void onClick() {
        placeClickListener.onPlaceClick(place.getLatitude(), place.getLongitude());
    }
}
