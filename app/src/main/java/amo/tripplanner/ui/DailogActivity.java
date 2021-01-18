package amo.tripplanner.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import amo.tripplanner.Helper.NotificationHelper;
import amo.tripplanner.R;
import amo.tripplanner.pojo.Note;
import amo.tripplanner.pojo.Trip;
import amo.tripplanner.reciver.AlarmRciever;
import amo.tripplanner.service.FloatingWidgetService;
import amo.tripplanner.viewmodel.TripListViewModel;

public class DailogActivity extends AppCompatActivity {

    EditText editText;
    TextView datetx, timetx;
    Button add;
    Button repeated;
    Button cancel;

    private int notificationId = 1;
    private int mYear, mMonth, mDay, currentHour, currentMin;
    private String amPm;
    private boolean isRestarted = false;

    private long timeInMilliseconds;
    private long dateInMilliseconds;
    private TripListViewModel listViewModels;
    private Trip trip = new Trip();
    private int tripId;
    private String tripName;
    private List<Note> noteList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailog);


        int tripId = getIntent().getIntExtra("TripID", 0);
        Log.i("DailogActivity", "onCreate: TripID: " + tripId);

        listViewModels = ViewModelProviders.of(this).get(TripListViewModel.class);

        listViewModels.getTripById(tripId).observe(this, trips -> {
            trip = trips;
            if (!isRestarted) {
                saveOnSharedPreference();
                isRestarted = true;
                Log.i("isRestarted", "onCreate: "+isRestarted);
            }
            Log.i("isRestarted", "onCreate: "+isRestarted);
            getDataFromSharedPreference();
            openDialog(this);
        });





        TripListViewModel listViewModels = ViewModelProviders.of(this).get(TripListViewModel.class);
        listViewModels.getNoteById(tripId).observe(this, trip -> {
            noteList = trip.getTripNotes();
            for(Note note:noteList){
                Log.i("FloatingWidgetService", "onStartCommand: "+note);
            }
        });





    }

    private void saveOnSharedPreference() {
        SharedPreferences preferences = getSharedPreferences("Trip", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("TripId", trip.getTripId());
        editor.putString("TripName", trip.getTripName());
        editor.commit();
    }

    private void getDataFromSharedPreference() {
        SharedPreferences preferences = getSharedPreferences("Trip", MODE_PRIVATE);
        tripId = preferences.getInt("TripId", 0);
        tripName = preferences.getString("TripName", "TripName");
    }

    @SuppressLint("ObsoleteSdkInt")
    public void openDialog(Context context) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(tripName);
        builder1.setMessage("It's time for your  trip " + tripName);
        builder1.setCancelable(false);
        builder1.setPositiveButton("START", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                double latitude1 = trip.getTripStartLocation().getLatitude();
                double longitude1 = trip.getTripStartLocation().getLongitude();
                double latitude2 = trip.getTripEndLocation().getLatitude();
                double longitude2 = trip.getTripEndLocation().getLongitude();

                String uri = "http://maps.google.com/maps?f=d&hl=en&saddr="+latitude1+","+longitude1+"&daddr="+latitude2+","+longitude2;
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                context.startActivity(Intent.createChooser(intent, "Select an application"));
                Intent intent1 = new Intent(getBaseContext(), FloatingWidgetService.class);
                intent1.putExtra("ListNotes", (Serializable) noteList);
                startService(intent1);
            }
        });

        builder1.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder1.setNeutralButton("SNOOZE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NotificationHelper notificationHelper = new NotificationHelper(context);
                NotificationCompat.Builder nb = notificationHelper.getChannelNotification(tripName);
                notificationHelper.getManager().notify(1, nb.build());
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

    @Override
    protected void onRestart() {
        super.onRestart();
        isRestarted = true;
    }
}