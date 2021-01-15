package amo.tripplanner.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapbox.mapboxsdk.Mapbox;
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;
import java.util.List;

import amo.tripplanner.Helper.FirebaseHelper;
import amo.tripplanner.R;
import amo.tripplanner.adapter.TripListAdapter;
import amo.tripplanner.databinding.FragmentHomeBinding;
import amo.tripplanner.pojo.Trip;
import amo.tripplanner.viewmodel.TripListViewModel;
import timber.log.Timber;


public class HomeFragment extends Fragment {


    private FragmentHomeBinding bindingHome;

    private TripListAdapter adapter;

    private TripListViewModel listViewModel;

    //Global Declaration
    private SNavigationDrawer sNavigationDrawer;

    private List<Trip> tripList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.i("onCreate: started.");


        Mapbox.getInstance(requireContext(), getString(R.string.access_token));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        bindingHome = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        onBackPressed();

        bindingHome.idRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new TripListAdapter();
        bindingHome.idRecyclerView.setAdapter(adapter);

        listViewModel = ViewModelProviders.of(this).get(TripListViewModel.class);
        listViewModel.getAllTrips().observe(getViewLifecycleOwner(), new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                tripList = trips;
                adapter.setTrips(tripList);
            }
        });

        deleteItemBySwabbing();

//        bindingHome.idBtnAddTrip.setOnClickListener(v -> Navigation.findNavController(container).navigate(R.id.action_homeFragment_to_addFragmentFragment));
        bindingHome.idBtnAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseHelper.getInstance(requireContext()).syncWithBackend(tripList);
            }
        });

        return bindingHome.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sNavigationDrawer = view.findViewById(R.id.navigationDrawer);
//        sNavigationDrawer.setMenu

        bindingHome.toolbar.toolbarNavDrawer.setOnClickListener(v -> sNavigationDrawer.openDrawer());

        //Creating a list of menu Items

        List<MenuItem> menuItems = new ArrayList<>();

        //Use the MenuItem given by this library and not the default one.
        //First parameter is the title of the menu item and then the second parameter is the image which will be the background of the menu item.

        menuItems.add(new MenuItem("News", R.drawable.arrow_left));
        menuItems.add(new MenuItem("Feed", R.drawable.arrow_left));
        menuItems.add(new MenuItem("Messages", R.drawable.arrow_left));
        menuItems.add(new MenuItem("Music", R.drawable.arrow_left));

        //then add them to navigation drawer

        sNavigationDrawer.setMenuItemList(menuItems);
        sNavigationDrawer.setScrollBarSize(0);

        //Listener to handle the menu item click. It returns the position of the menu item clicked. Based on that you can switch between the fragments.

//        sNavigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
//            @Override
//            public void onMenuItemClicked(int position) {
//                System.out.println("Position " + position);

//                switch (position) {
//                    case 0: {
//                        fragmentClass = NewsFragment.class;
//                        break;
//                    }
//                    case 1: {
//                        fragmentClass = FeedFragment.class;
//                        break;
//                    }
//                    case 2: {
//                        fragmentClass = MessagesFragment.class;
//                        break;
//                    }
//                    case 3: {
//                        fragmentClass = MusicFragment.class;
//                        break;
//                    }
//
//                }

//                //Listener for drawer events such as opening and closing.
//                sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {
//
//                    @Override
//                    public void onDrawerOpened() {
//
//                    }
//
//                    @Override
//                    public void onDrawerOpening() {
//
//                    }
//
//                    @Override
//                    public void onDrawerClosing() {
//                        System.out.println("Drawer closed");
//
////                       Navigation.findNavController(view).navigate(R.id.);
////                        fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
//
//
//                    }
//
//                    @Override
//                    public void onDrawerClosed() {
//
//                    }
//
//                    @Override
//                    public void onDrawerStateChanged(int newState) {
//                        System.out.println("State " + newState);
//                    }
//                });
//            }
//        });
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


    private void onBackPressed() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), callback);
    }


}