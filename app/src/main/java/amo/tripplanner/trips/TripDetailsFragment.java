package amo.tripplanner.trips;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import amo.tripplanner.R;
import amo.tripplanner.databinding.FragmentTripDetailsBinding;


public class TripDetailsFragment extends Fragment {


    FragmentTripDetailsBinding tripDetailsBinding;

    public TripDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        tripDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_trip_details, container, false);

        return tripDetailsBinding.getRoot();
    }
}