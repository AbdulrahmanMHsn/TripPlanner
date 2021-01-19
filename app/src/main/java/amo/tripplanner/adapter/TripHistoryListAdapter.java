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

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import amo.tripplanner.R;
import amo.tripplanner.databinding.ItemTripBinding;
import amo.tripplanner.databinding.ItemTripHistoryBinding;
import amo.tripplanner.pojo.Trip;
import amo.tripplanner.service.FloatingWidgetService;
import amo.tripplanner.ui.home.HomeFragmentDirections;

public class TripHistoryListAdapter extends RecyclerView.Adapter<TripHistoryListAdapter.TripViewHolder> {

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

    public TripHistoryListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = parent;
        ItemTripHistoryBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_trip_history, parent, false);

        return new TripViewHolder(itemBinding);
    }

    @SuppressLint({"LogNotTimber", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {

        if (trips.isEmpty()) {
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
        String s = new SimpleDateFormat("dd/MM/yyyy HH:mm aa").format(time);
        String[] timeDate = s.split(" ");
        String dateTxt = timeDate[0];
        String timeTxt = timeDate[1];
        String amPmTxt = timeDate[2];
        Log.i(TAG, "onBindViewHolder: item.getTripStatus() " + s);
        holder.itemBinding.itemTxtVwTime.setText(covertTimeTo12Hours(timeTxt) + " " + amPmTxt);
        holder.itemBinding.itemTxtVwDate.setText(dateTxt);


//        holder.itemBinding.itemTxtVwTripMenu.setOnClickListener(v -> {
//            HomeFragmentDirections.ActionHomeFragmentToEditTripFragment action = HomeFragmentDirections.actionHomeFragmentToEditTripFragment();
//            action.setId(item.getTripId());
//            action.setName(item.getTripName());
//            action.setStartPoint(item.getTripStartLocation().getAddress());
//            action.setEndPoint(item.getTripEndLocation().getAddress());
//            action.setTimestamp(item.getTripTimestamp());
//            action.setIsRounded(item.isTripIsRound());
//            action.setRepeat(item.getTripRepeat());
//            Navigation.findNavController(view).navigate(action);
//        });


        holder.itemBinding.ItemLinearLayout.setOnClickListener(view -> {
            if (mListener != null) {
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        });


//        holder.itemBinding.itemBtnStartTrip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                double latitude1 = item.getTripStartLocation().getLatitude();
//                double longitude1 = item.getTripStartLocation().getLongitude();
//                double latitude2 = item.getTripEndLocation().getLatitude();
//                double longitude2 = item.getTripEndLocation().getLongitude();
//
//                String uri = "http://maps.google.com/maps?f=d&hl=en&saddr="+latitude1+","+longitude1+"&daddr="+latitude2+","+longitude2;
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                context.startActivity(Intent.createChooser(intent, "Select an application"));
//                Intent intent1 = new Intent(v.getContext(), FloatingWidgetService.class);
//                intent1.putExtra("ListNotes", (Serializable) item.getTripNotes());
//                v.getContext().startService(intent1);
//            }
//        });


//        holder.itemBinding.itemImgTripNote.setOnClickListener(v -> {
//            HomeFragmentDirections.ActionHomeFragmentToNoteFragment action = HomeFragmentDirections.actionHomeFragmentToNoteFragment();
//            action.setId(item.getTripId());
//            Navigation.findNavController(view).navigate(action);
//        });

    }

    private String covertTimeTo12Hours(String time) {
        String[] splitTime = time.split(":");
        String time12 = splitTime[1];

        switch (splitTime[0]) {
            case "12":
                return "12:" + time12;
            case "13":
                return "01:" + time12;

            case "14":
                return "02:" + time12;

            case "15":
                return "03:" + time12;

            case "16":
                return "04:" + time12;

            case "17":
                return "05:" + time12;

            case "18":
                return "06:" + time12;
            case "19":
                return "07:" + time12;
            case "20":
                return "08:" + time12;
            case "21":
                return "09:" + time12;
            case "22":
                return "10:" + time12;
            case "23":
                return "11:" + time12;
        }
        return time;
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
        ItemTripHistoryBinding itemBinding;

        public TripViewHolder(@NonNull ItemTripHistoryBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

    }
}
