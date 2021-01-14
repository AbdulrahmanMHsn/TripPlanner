package amo.tripplanner.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.mapbox.mapboxsdk.Mapbox;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import amo.tripplanner.R;
import amo.tripplanner.adapter.TripListAdapter;
import amo.tripplanner.databinding.FragmentHomeBinding;
import amo.tripplanner.pojo.Trip;
import amo.tripplanner.viewmodel.TripListViewModel;


public class HomeFragment extends Fragment {


    private static final String TAG = "HomeFragment";
    FragmentHomeBinding bindingHome;

    private RecyclerView recyclerView;
    private TripListAdapter adapter;
    View view;

    DatabaseReference reference;
    List<Trip> list = new ArrayList<>();
    TripListViewModel listViewModel;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: started.");
        Mapbox.getInstance(getContext(), getString(R.string.access_token));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        bindingHome = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        bindingHome.idRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new TripListAdapter();
        bindingHome.idRecyclerView.setAdapter(adapter);

        listViewModel = ViewModelProviders.of(this).get(TripListViewModel.class);
        listViewModel.getAllTrips().observe(getViewLifecycleOwner(), new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                Log.i(TAG, "onChanged: List<Trip> trips"+trips.size());
                adapter.setTrips(trips);
            }
        });

        deleteItemBySwabbing();

        bindingHome.idBtnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(container).navigate(R.id.action_homeFragment_to_addFragmentFragment);
            }
        });

        return bindingHome.getRoot();
    }


    private void deleteItemBySwabbing() {
        // Delete subject by swabbing item left and right
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @SuppressLint("NewApi")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                final Trip subject = adapter.getItem(position);
                listViewModel.delete(subject);
            }
        });
        itemTouchHelper.attachToRecyclerView(bindingHome.idRecyclerView);
    }


}