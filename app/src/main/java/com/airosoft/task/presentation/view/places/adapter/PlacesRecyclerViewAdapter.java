package com.airosoft.task.presentation.view.places.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.airosoft.task.R;
import com.airosoft.task.model.local.PlaceModel;
import com.airosoft.task.presentation.listener.OnPlaceClickListener;

import java.util.ArrayList;
import java.util.List;

public class PlacesRecyclerViewAdapter extends RecyclerView.Adapter<PlacesViewHolder> {
    private OnPlaceClickListener placeClickListener;
    private List<PlaceModel> placeList;

    public PlacesRecyclerViewAdapter(OnPlaceClickListener listener) {
        placeClickListener = listener;
        placeList = new ArrayList<>();
    }

    @NonNull
    @Override
    public PlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlacesViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_place_list, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesViewHolder holder, int position) {
        holder.onBind(placeClickListener, placeList.get(position));
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public void setPlaceList(List<PlaceModel> placeList) {
        this.placeList = placeList;
        notifyDataSetChanged();
    }
}
