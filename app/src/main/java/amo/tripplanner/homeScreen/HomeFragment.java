package amo.tripplanner.homeScreen;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Queue;

import amo.tripplanner.R;
import amo.tripplanner.databinding.FragmentAddFragmentBinding;
import amo.tripplanner.databinding.FragmentHomeBinding;
import amo.tripplanner.databinding.FragmentLoginBinding;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    View view;
    //ArrayList<TripPojo> list;

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference tripref=db.collection("Trip");


    FragmentHomeBinding bindingHome;


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

       FloatingActionButton buttonAddTrip=bindingHome.idBtnAddTrip;

        Query query=tripref.orderBy("date",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<TripPojo> options=new FirestoreRecyclerOptions.Builder<TripPojo>()
                .setQuery(query,TripPojo.class).build();
        adapter= new RecyclerViewAdapter(options);
        recyclerView=bindingHome.idRecyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        view=container;


        bindingHome.idBtnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(container).navigate(R.id.action_homeFragment_to_addFragmentFragment);

            }
        });

        return bindingHome.getRoot();

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}