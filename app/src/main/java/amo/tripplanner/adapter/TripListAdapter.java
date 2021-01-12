package amo.tripplanner.adapter;


import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import amo.tripplanner.R;
import amo.tripplanner.databinding.TripsItemBinding;
import amo.tripplanner.pojo.Trip;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.TripViewHolder> {

    private static final String TAG = "TripListAdapter";
    private List<Trip> trips = new ArrayList<>();

    private OnItemClickListener mListener;

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
        String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
        Log.i(TAG, "onBindViewHolder: item.getTripStatus() "+ s);

        holder.itemBinding.ItemLinearLayout.setOnClickListener(view -> {
            Log.d(TAG, "onClick: navigating to card subject and pass data.");
//                Intent intent = new Intent(context, CardActivity.class);
//                intent.putExtra("SUBJECT_EXTRA_ID", list.get(position).getId());
//                context.startActivity(intent);
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
