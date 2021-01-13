package amo.tripplanner.adapter;


import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import amo.tripplanner.R;
import amo.tripplanner.databinding.TripsItemBinding;
import amo.tripplanner.pojo.Trip;
import amo.tripplanner.ui.home.HomeFragmentDirections;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.TripViewHolder> {

    private static final String TAG = "TripListAdapter";
    private List<Trip> trips = new ArrayList<>();

    private OnItemClickListener mListener;
    private View view;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public TripListAdapter() {
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = parent;
        TripsItemBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.trips_item, parent, false);

        return new TripViewHolder(itemBinding);
    }

    @SuppressLint("LogNotTimber")
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
        holder.itemBinding.itemTxtVwStatus.setText(item.getTripStatus());
        long time = item.getTripTimestamp();
        @SuppressLint("SimpleDateFormat")
        String s = new SimpleDateFormat("yyyy-MM-dd HH:mm aa").format(time);
        Log.i(TAG, "onBindViewHolder: item.getTripStatus() "+ s);

        holder.itemBinding.itemTxtVwTripMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragmentDirections.ActionHomeFragmentToEditTripFragment action = HomeFragmentDirections.actionHomeFragmentToEditTripFragment();
                action.setId(item.getTripId());
                action.setName(item.getTripName());
                action.setStartPoint(item.getTripStartLocation().getAddress());
                action.setEndPoint(item.getTripEndLocation().getAddress());
                action.setTimestamp(item.getTripTimestamp());
                action.setIsRounded(item.isTripIsRound());
                action.setRepeat(item.getTripRepeat());
                Navigation.findNavController(view).navigate(action);
            }
        });

        holder.itemBinding.ItemLinearLayout.setOnClickListener(view -> {
            if (mListener != null) {
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        });

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

    /**
     * Method to get item by position.
     *
     * @param position
     * @return
     */
    @Nullable
    public Trip getItem(int position) {
        return trips.get(position);
    }

    static class TripViewHolder extends RecyclerView.ViewHolder {
        TripsItemBinding itemBinding;

        public TripViewHolder(@NonNull TripsItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

    }
}
