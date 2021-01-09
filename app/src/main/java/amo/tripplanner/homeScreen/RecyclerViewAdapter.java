package amo.tripplanner.homeScreen;

import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.IconCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

import amo.tripplanner.R;
import amo.tripplanner.databinding.FragmentHomeBinding;


public class RecyclerViewAdapter extends FirestoreRecyclerAdapter<TripPojo, RecyclerViewAdapter.ViewHolder> {


    public RecyclerViewAdapter(@NonNull FirestoreRecyclerOptions<TripPojo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull TripPojo model) {
        holder.tripName.setText(model.getTripName());
        holder.tripDate.setText((CharSequence) model.getTripDate());
        holder.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_item,parent,false);
        return new ViewHolder(view);
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tripName;
        TextView tripDate;
        Button start;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tripName=itemView.findViewById(R.id.id_trip_name);
            tripDate=itemView.findViewById(R.id.id_trip_date);
            start=itemView.findViewById(R.id.id_btn_start);
        }

    }
}

