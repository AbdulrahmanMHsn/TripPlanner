package amo.tripplanner.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import amo.tripplanner.R;
import amo.tripplanner.databinding.TripsItemBinding;
import amo.tripplanner.pojo.Trip;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.TripViewHolder> {

    private static final String TAG = "TripListAdapter";
    private Context context;
    private List<Trip> trips;

    public TripListAdapter() {
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TripsItemBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.trips_item, parent, false);

        return new TripViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        
        if(trips.isEmpty()){
            Log.i(TAG, "onBindViewHolder: my list is empty");
           return; 
        }
        Trip item = trips.get(position);
        holder.itemBinding.itemTxtVwName.setText(item.getTripName());
        holder.itemBinding.itemTxtVwStartPoint.setText(item.getTripStartLocation().getAddress());
        holder.itemBinding.itemTxtVwEndPoint.setText(item.getTripEndLocation().getAddress());
    }


    public void setTrips(List<Trip> trips) {
        this.trips = trips;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (trips.size() != 0)
            return trips.size();
        else
            return 0;
    }

    static class TripViewHolder extends RecyclerView.ViewHolder {
        TripsItemBinding itemBinding;

        public TripViewHolder(@NonNull TripsItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

    }
}
