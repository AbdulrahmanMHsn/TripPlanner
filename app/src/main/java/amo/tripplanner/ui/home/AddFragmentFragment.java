package amo.tripplanner.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

//import com.google.android.libraries.places.api.Places;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.FirebaseFirestore;

import amo.tripplanner.R;
import amo.tripplanner.databinding.FragmentAddFragmentBinding;


public class AddFragmentFragment extends Fragment {

    String tripName,startPoint,endPoint,date,time;
    CheckBox checkBox;

    boolean selected;

    FragmentAddFragmentBinding binding;
    public AddFragmentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_fragment, container, false);
        startPoint=binding.idEditStartPoint.getText().toString();
        endPoint=binding.idEditEndPoint.getText().toString();

        //initialize places
      //  Places.initialize(binding.get);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.new_trip,menu);
       super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_trip:
                addTrip();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void addTrip(){
        tripName=binding.idEditName.getText().toString();
        startPoint=binding.idEditStartPoint.getText().toString();
        endPoint=binding.idEditEndPoint.getText().toString();
        date=binding.idEdtDate.getText().toString();
        time=binding.idEdtTime.getText().toString();
        if(tripName.trim().isEmpty()){
            Toast.makeText(getActivity(), "Enter Trip Name", Toast.LENGTH_SHORT).show();
            return;
        }
        if(startPoint.trim().isEmpty()){
            Toast.makeText(getActivity(), "Enter startPoint", Toast.LENGTH_SHORT).show();
            return;
        }
        if(endPoint.trim().isEmpty()){
            Toast.makeText(getActivity(), "Enter endPoint", Toast.LENGTH_SHORT).show();
            return;
        }
        if(date.trim().isEmpty()){
            Toast.makeText(getActivity(), "Enter Trip date", Toast.LENGTH_SHORT).show();
            return;
        }
        if(time.trim().isEmpty()){
            Toast.makeText(getActivity(), "Enter Trip time", Toast.LENGTH_SHORT).show();
            return;
        }
//        CollectionReference tripref= FirebaseFirestore.getInstance()
//                .collection("Trip");
//        tripref.add(new TripPojo( date,  tripName, time, endPoint, startPoint));
//        Toast.makeText(getActivity(), "Trip Added", Toast.LENGTH_SHORT).show();

    }
}