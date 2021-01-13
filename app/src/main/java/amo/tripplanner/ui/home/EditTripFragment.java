package amo.tripplanner.ui.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.PlaceAutocomplete;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.model.PlaceOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import amo.tripplanner.R;
import amo.tripplanner.databinding.LayoutAddFragmentBinding;
import amo.tripplanner.databinding.LayoutEditFragmentBinding;
import amo.tripplanner.pojo.Location;
import amo.tripplanner.pojo.Trip;
import amo.tripplanner.viewmodel.TripListViewModel;


public class EditTripFragment extends Fragment {

    //vars
    public static final String TAG = "EditTripFragment";
    private long timeInMilliseconds;
    private long dateInMilliseconds;
    private boolean isStart;
    private String[] repeats = {"No Repeat", "Repeat Daily", "Repeat Monthly", "Repeat Weekly"};

    //vars trip
    private int id;
    private String tripName;
    private String startAddress, endAddress;
    private double startLatitude, startLongitude;
    private double endLatitude, endLongitude;
    private long timestamp;
    private String tripStatus = "Upcoming";
    private boolean tripIsRound = false;
    private String tripRepeat;

    //vars static
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;

    //biding
    private LayoutEditFragmentBinding binding;

    public EditTripFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_edit_fragment, container, false);

        if (getArguments() != null) {
            EditTripFragmentArgs args = EditTripFragmentArgs.fromBundle(getArguments());
            id = args.getId();
            tripName = args.getName();
            startAddress = args.getStartPoint();
            endAddress = args.getEndPoint();
            timestamp = args.getTimestamp();
            tripIsRound = args.getIsRounded();
            tripRepeat = args.getRepeat();

            binding.editTripEdTxtVwTripName.setText(tripName);
            binding.editTripEdTxtVwTripStartPoint.setText(startAddress);
            binding.editTripEdTxtVwTripEndPoint.setText(endAddress);
            binding.editTripChBoxRounded.setChecked(tripIsRound);
        }
//        binding.editTripSpnChoose.set(tripIsRound);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.editTripSpnChoose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tripRepeat = repeats[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                tripRepeat = repeats[0];
            }
        });

        //Creating the ArrayAdapter instance having the country list
        @SuppressLint("UseRequireInsteadOfGet")
        ArrayAdapter aa = new ArrayAdapter(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, repeats);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        binding.editTripSpnChoose.setAdapter(aa);

        // choose start location trip
        binding.editTripEdTxtVwTripStartPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAutoComplete();
                isStart = true;
            }
        });


        binding.editTripChBoxRounded.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tripIsRound = isChecked;
            }
        });

        // choose end location trip
        binding.editTripEdTxtVwTripEndPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAutoComplete();
            }
        });

        // choose date trip
        binding.editTripEdTxtVwTripDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        // choose time trip
        binding.editTripEdTxtVwTripTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        // add trip
        binding.editTripBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tripName = binding.editTripEdTxtVwTripName.getText().toString();
                timestamp = timeInMilliseconds + dateInMilliseconds;

                Location startLocation = new Location(startAddress, startLatitude, startLongitude);
                Location endLocation = new Location(endAddress, endLatitude, endLongitude);

                Trip trip = new Trip(tripName, startLocation, endLocation, timestamp, tripStatus, tripIsRound, tripRepeat);
                trip.setTripId(id);
                updateTrip(trip);
                Navigation.findNavController(view).navigate(R.id.action_editTripFragment_to_homeFragment);
            }
        });

        // naviagting to home fragment
        binding.editTripBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(view).navigate(R.id.action_editTripFragment_to_homeFragment);
            }
        });

        binding.editTripImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_editTripFragment_to_homeFragment);
            }
        });

    }


    /*
     * A function use to update trip within room database
     * */
    private void updateTrip(Trip trip) {
        // call observe
        TripListViewModel listViewModel = ViewModelProviders.of(this).get(TripListViewModel.class);
        listViewModel.update(trip);
    }


    /*
     * A function use to open TimePickerDialog
     * */
    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog,
                new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SimpleDateFormat")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        Date date = null;
                        try {
                            date = new SimpleDateFormat("HH:mm").parse(hourOfDay + ":" + minute);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (date != null) {
                            binding.editTripEdTxtVwTripTime.setText(new SimpleDateFormat("hh : mm aa").format(date));
                            timeInMilliseconds = date.getTime();
                        }


                    }
                }, 12, 0, false
        );
        Objects.requireNonNull(timePickerDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.show();
    }

    /*
     * A function use to open DatePickerDialog
     * */
    private void showDatePickerDialog() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(requireContext(), android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int months = month + 1;
                Date date = null;
                try {
                    date = new SimpleDateFormat("dd/MM/yyyy").parse(months + "/" + dayOfMonth + "/" + year);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (date != null) {
                    binding.editTripEdTxtVwTripDate.setText(new SimpleDateFormat("dd / MM / yyyy").format(date));
                    dateInMilliseconds = date.getTime();
                }

            }
        }, year, month, day);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }


    /*
     * A function use to open Fragment PlaceAutocomplete
     * */
    private void goToAutoComplete() {
        Intent intent = new PlaceAutocomplete.IntentBuilder()
                .accessToken(Mapbox.getAccessToken() != null ? Mapbox.getAccessToken() : getString(R.string.access_token))
                .placeOptions(PlaceOptions.builder()
                        .backgroundColor(Color.parseColor("#EEEEEE"))
                        .limit(10)
                        .build(PlaceOptions.MODE_CARDS))
                .build(getActivity());
        startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_AUTOCOMPLETE) {

            assert data != null;

            String address = PlaceAutocomplete.getPlace(data).placeName();

            LatLng latLng = new LatLng(((Point) Objects.requireNonNull(PlaceAutocomplete.getPlace(data).geometry())).latitude(),
                    ((Point) Objects.requireNonNull(PlaceAutocomplete.getPlace(data).geometry())).longitude());

            if (isStart) {
                startAddress = address;
                startLatitude = latLng.getLatitude();
                startLongitude = latLng.getLongitude();
                binding.editTripEdTxtVwTripStartPoint.setText(startAddress);
                isStart = false;
            } else {
                endAddress = address;
                endLatitude = latLng.getLatitude();
                endLongitude = latLng.getLongitude();
                binding.editTripEdTxtVwTripEndPoint.setText(endAddress);
            }


            Toast.makeText(getContext(), latLng.getLatitude() + "  " + latLng.getLongitude(), Toast.LENGTH_SHORT).show();

        }
    }
}