package amo.tripplanner.ui.home;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

import amo.tripplanner.R;
import amo.tripplanner.pojo.TripPojo;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<TripPojo> trips;

    public RecyclerViewAdapter(Context c, ArrayList<TripPojo> t) {
        context = c;
        trips = t;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.trip_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tripName.setText(trips.get(position).getTripName());
        holder.tripDate.setText(trips.get(position).getStartPoint());
//        holder.tripTime.setText(trips.get(position).getEndPoint());
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

   static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tripName;
        TextView tripDate;
        TextView tripTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tripName=itemView.findViewById(R.id.id_trip_name);
            tripDate=itemView.findViewById(R.id.id_trip_date);
            tripTime=itemView.findViewById(R.id.id_edt_Time);
        }

    }
}
