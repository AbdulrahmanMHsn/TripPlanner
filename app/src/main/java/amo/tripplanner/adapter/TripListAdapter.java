package amo.tripplanner.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import amo.tripplanner.databinding.ItemTripBinding;
import amo.tripplanner.databinding.TripsItemBinding;
import amo.tripplanner.pojo.Trip;
import amo.tripplanner.service.FloatingWidgetService;
import amo.tripplanner.ui.home.HomeFragmentDirections;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.TripViewHolder> {

    Context context;
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

    public TripListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = parent;
        ItemTripBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_trip, parent, false);

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


        holder.itemBinding.itemTxtVwTripMenu.setOnClickListener(v -> {
            HomeFragmentDirections.ActionHomeFragmentToEditTripFragment action = HomeFragmentDirections.actionHomeFragmentToEditTripFragment();
            action.setId(item.getTripId());
            action.setName(item.getTripName());
            action.setStartPoint(item.getTripStartLocation().getAddress());
            action.setEndPoint(item.getTripEndLocation().getAddress());
            action.setTimestamp(item.getTripTimestamp());
            action.setIsRounded(item.isTripIsRound());
            action.setRepeat(item.getTripRepeat());
            Navigation.findNavController(view).navigate(action);
        });


        holder.itemBinding.ItemLinearLayout.setOnClickListener(view -> {
            if (mListener != null) {
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        });

        holder.itemBinding.itemBtnStartTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double latitude1 = item.getTripStartLocation().getLatitude();
                double longitude1 = item.getTripStartLocation().getLongitude();
                double latitude2 = item.getTripEndLocation().getLatitude();
                double longitude2 = item.getTripEndLocation().getLongitude();

                String uri = "http://maps.google.com/maps?f=d&hl=en&saddr="+latitude1+","+longitude1+"&daddr="+latitude2+","+longitude2;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                context.startActivity(Intent.createChooser(intent, "Select an application"));
                Intent intent1 =new Intent(v.getContext(), FloatingWidgetService.class);
                v.getContext().startService(intent1);
            }
        });


        holder.itemBinding.itemImgTripNote.setOnClickListener(v -> {
            HomeFragmentDirections.ActionHomeFragmentToNoteFragment action = HomeFragmentDirections.actionHomeFragmentToNoteFragment();
            action.setId(item.getTripId());
            Navigation.findNavController(view).navigate(action);
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
        ItemTripBinding itemBinding;

        public TripViewHolder(@NonNull ItemTripBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

    }
}
