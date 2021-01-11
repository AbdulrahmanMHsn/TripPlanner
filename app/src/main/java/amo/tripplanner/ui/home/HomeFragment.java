package amo.tripplanner.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.List;

import amo.tripplanner.R;
import amo.tripplanner.adapter.TripListAdapter;
import amo.tripplanner.databinding.FragmentHomeBinding;
import amo.tripplanner.databinding.TripItemBinding;
import amo.tripplanner.pojo.Location;
import amo.tripplanner.pojo.Trip;
import amo.tripplanner.pojo.TripPojo;


public class HomeFragment extends Fragment {

    FragmentHomeBinding bindingHome;
//    TripItemBinding itemBinding;

    private RecyclerView recyclerView;
    private TripListAdapter adapter;
//    ImageButton imageButton=itemBinding.idIconList;
    View view;

    DatabaseReference reference;
    List<Trip> list = new ArrayList<>();

//    private FirebaseFirestore db=FirebaseFirestore.getInstance();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        bindingHome = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);


        bindingHome.idRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        list.add(new Trip("assa",new Location("eldohi",1,1),new Location("eldohi",1,1)));
        list.add(new Trip("assa",new Location("eldohi",1,1),new Location("eldohi",1,1)));
        list.add(new Trip("assa",new Location("eldohi",1,1),new Location("eldohi",1,1)));
        list.add(new Trip("assa",new Location("eldohi",1,1),new Location("eldohi",1,1)));

        adapter=new TripListAdapter();
        bindingHome.idRecyclerView.setAdapter(adapter);
        adapter.setTrips(list);

//        reference= FirebaseDatabase.getInstance().getReference().child("Trip");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
//                    TripPojo tripPojo=snapshot.getValue(TripPojo.class);
//                    list.add(tripPojo);
//                }
//
//            }

//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getActivity(), "Opsss.....SomeThing is wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        view=container;


//        BottomSheetMenu bottomSheetMenu=new BottomSheetMenu();
//        bottomSheetMenu.show(getFragmentManager(),"bottomsheet");
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

//        bindingHome.idBtnAddTrip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Navigation.findNavController(container).navigate(R.id.action_homeFragment_to_addFragmentFragment);
//            }
//        });

        return bindingHome.getRoot();
    }


}