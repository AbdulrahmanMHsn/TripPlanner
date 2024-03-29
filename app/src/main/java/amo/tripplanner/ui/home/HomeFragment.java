package amo.tripplanner.ui.home;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.mapbox.mapboxsdk.Mapbox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import amo.tripplanner.Helper.Dialogs;
import amo.tripplanner.Helper.FirebaseHelper;
import amo.tripplanner.Helper.NotificationHelper;
import amo.tripplanner.R;
import amo.tripplanner.adapter.TripListAdapter;
import amo.tripplanner.databinding.FragmentHomeBinding;
import amo.tripplanner.pojo.Trip;
import amo.tripplanner.reciver.AlarmRciever;
import amo.tripplanner.service.FloatingWidgetService;
import amo.tripplanner.viewmodel.TripListViewModel;
import timber.log.Timber;

import static android.content.Context.ALARM_SERVICE;

public class HomeFragment extends Fragment {

    DrawerLayout drawerLayout;
    NavigationView navigationView;


    private FragmentHomeBinding bindingHome;
    private Dialog mProgress;
    private TripListAdapter adapter;

    private TripListViewModel listViewModel;

    private List<Trip> tripList = new ArrayList<>();
    private List<Trip> tripListHistory = new ArrayList<>();

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

        mProgress = Dialogs.createProgressBarDialog(getContext(), "");
        drawerLayout = bindingHome.drawerLayout;

        View viewHeader = bindingHome.navView.getHeaderView(0);
        TextView textView = viewHeader.findViewById(R.id.text_email_header);
        textView.setText(FirebaseHelper.getInstance(getContext()).getmEmail());
        bindingHome.navView.getMenu().getItem(4).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                FirebaseHelper.getInstance(getContext()).logOut();
                Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_loginFragment);
                drawerLayout.close();
                return false;
            }
        });

        bindingHome.navView.getMenu().getItem(1).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Navigation.findNavController(getView()).navigate(R.id.action_homeFragment_to_historyFragment);
                drawerLayout.close();
                return false;
            }
        });


        bindingHome.navView.getMenu().getItem(0).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                drawerLayout.close();
                return false;
            }
        });


        bindingHome.toolbar.toolbarNavDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        bindingHome.idRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new TripListAdapter(getContext());
        bindingHome.idRecyclerView.setAdapter(adapter);

        listViewModel = ViewModelProviders.of(this).get(TripListViewModel.class);
        listViewModel.getAllTrips().observe(getViewLifecycleOwner(), trips -> {
            tripList = trips;
            adapter.setTrips(tripList);
        });


        listViewModel.getAllHistoryTrips().observe(getViewLifecycleOwner(), trips -> {
            tripListHistory = trips;
//            adapter.setTrips(tripList);
        });

        deleteItemBySwabbing();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(getContext())) {
                getPermission();
            }
        }

        bindingHome.idBtnAddTrip.setOnClickListener(v -> Navigation.findNavController(container).navigate(R.id.action_homeFragment_to_addFragmentFragment));
//        bindingHome.idBtnAddTrip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                FirebaseHelper.getInstance(requireContext()).syncWithBackend(tripList);
//                Navigation.findNavController(container).navigate(R.id.action_homeFragment_to_historyFragment);
//            }
//        });
        return bindingHome.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        bindingHome.navView.getMenu().getItem(2).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                FirebaseHelper.getInstance(getContext()).syncWithBackend(tripList, tripListHistory,mProgress);
//                FirebaseHelper.getInstance(getContext()).syncWithBackend(tripListHistory);
                return false;
            }
        });


       /* bindingHome.toolbar.toolbarNavDrawer.setOnClickListener(v -> {
//               bindingHome.drawer.open();
        });*/

    }

    private void deleteItemBySwabbing() {
        // Delete subject by swabbing item left and right
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(90, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @SuppressLint("NewApi")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                final Trip trip = adapter.getItem(position);
                assert trip != null;
                openDialog(getContext(),trip);
//                cancelAlarm(trip.getTripId());
//                listViewModel.delete(trip);
            }
        });
        itemTouchHelper.attachToRecyclerView(bindingHome.idRecyclerView);
    }

    private void onBackPressed() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (bindingHome.drawerLayout.isOpen()) {
                    bindingHome.drawerLayout.close();
                } else {
                    requireActivity().finish();
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), callback);
    }

    public void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(getContext())) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + requireActivity().getPackageName()));
            startActivityForResult(intent, 1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(getContext())) {
                    Toast.makeText(getContext(), "permission denied by user.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void cancelAlarm(int id) {
        AlarmManager alarmManager = (AlarmManager) requireActivity().getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(requireActivity(), AlarmRciever.class);
        final PendingIntent pendingIntent = PendingIntent
                .getBroadcast(requireContext(), id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

    public void openDialog(Context context, Trip trip) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle("Are you sure delete trip " + trip.getTripName() + " ? ");
        builder1.setCancelable(false);
        builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                cancelAlarm(trip.getTripId());
                listViewModel.delete(trip);
            }
        });

        builder1.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.setTrips(tripList);
               dialog.cancel();
            }
        });


        AlertDialog dialog = builder1.create();
        if (dialog.getWindow() != null) {
            int type;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                type = WindowManager.LayoutParams.TYPE_TOAST;
            } else {
                type = WindowManager.LayoutParams.TYPE_PHONE;
            }
            dialog.getWindow().setType(type);
            Objects.requireNonNull(dialog.getWindow()).setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
            dialog.show();
        }
    }
}