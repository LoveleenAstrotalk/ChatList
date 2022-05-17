package com.astrotalk.sdk.api.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.astrotalk.sdk.R;
import com.astrotalk.sdk.api.googleApi.GoogleAddressModel;

import java.util.ArrayList;

public class AstroNewSearchPlaceAdapter extends RecyclerView.Adapter<AstroNewSearchPlaceAdapter.ViewHolder> {

    Context context;
    ArrayList<GoogleAddressModel> locationResultList;
    private final AstroNewSearchPlaceAdapter.LocationItemClick locationItemClick;

    public AstroNewSearchPlaceAdapter(Context context, ArrayList<GoogleAddressModel> locationResultList,
                                      AstroNewSearchPlaceAdapter.LocationItemClick locationItemClick
    ) {
        this.context = context;
        this.locationResultList = locationResultList;
        this.locationItemClick = locationItemClick;
    }

    @NonNull
    @Override
    public AstroNewSearchPlaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.at_matchmaking_location_adapter, parent, false);
        return new AstroNewSearchPlaceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AstroNewSearchPlaceAdapter.ViewHolder holder, int position) {

        if (position == RecyclerView.NO_POSITION) {
            return;
        }

        GoogleAddressModel googleAddressModel = locationResultList.get(position);

        if (googleAddressModel.getState().equalsIgnoreCase("")) {
            holder.tv_location.setText(googleAddressModel.getCity() + ", " + googleAddressModel.getCountry());
        } else {
            holder.tv_location.setText(googleAddressModel.getCity() + ", " + googleAddressModel.getState() + ", " + googleAddressModel.getCountry());
        }

        holder.ll_parent.setOnClickListener(v -> {
            if (googleAddressModel.isNew()) {
                Log.e("Response", "new Model");
            } else {
                if (locationResultList != null && locationResultList.size() > 0) {
                    locationItemClick.onLocationItemClick(googleAddressModel, position, locationResultList.get(position).getCity() + ", " + locationResultList.get(position).getState() + ", " + locationResultList.get(position).getCountry());
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        if (locationResultList.size() > 0) {
            return locationResultList.size();
        } else {
            return 0;
        }
    }


    public interface LocationItemClick {
        void onLocationItemClick(GoogleAddressModel locationResultList, int position, String address);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_location;
        LinearLayout ll_parent;

        public ViewHolder(View convertView) {
            super(convertView);

            ll_parent = convertView.findViewById(R.id.ll_parent);
            tv_location = convertView.findViewById(R.id.tv_location);
        }
    }
}