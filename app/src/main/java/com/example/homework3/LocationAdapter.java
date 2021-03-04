package com.example.homework3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private List<Location> locations;

    public LocationAdapter(List<Location> locationList){
        this.locations = locationList;
    }

    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View locationView = inflater.inflate(R.layout.item_location, parent, false);
        ViewHolder viewHolder = new ViewHolder(locationView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder holder, int position) {
        Location location = locations.get(position);
        holder.textView1.setText(location.getName());
        holder.textView2.setText(location.getType());
        holder.textView3.setText(location.getDimension());
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView1, textView2, textView3;

        public ViewHolder(View itemView){
            super(itemView);

            textView1 = itemView.findViewById(R.id.textView_fraglocationName);
            textView2 = itemView.findViewById(R.id.textView_fragLocationType);
            textView3 = itemView.findViewById(R.id.textView_fragLocationDimension);
        }
    }
}
