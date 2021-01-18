package amo.tripplanner.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import amo.tripplanner.Helper.NotificationHelper;
import amo.tripplanner.R;
import amo.tripplanner.reciver.AlarmRciever;

public class DailogActivity extends AppCompatActivity {

    EditText editText;
    TextView datetx,timetx;
    Button add;
    Button repeated;
    Button cancel;

    private int notificationId=1;
    private int mYear, mMonth, mDay,currentHour,currentMin;
    private String amPm;

    private long timeInMilliseconds;
    private long dateInMilliseconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailog);


        openDialog(this);
        // Create the object of
        // AlertDialog Builder class
//        AlertDialog.Builder builder
//                = new AlertDialog
//                .Builder(DailogActivity.this);
//
//        // Set the message show for the Alert time
//        builder.setMessage("Do you want to exit ?");
//
//        // Set Alert Title
//        builder.setTitle("Alert !");
//
//        // Set Cancelable false
//        // for when the user clicks on the outside
//        // the Dialog Box then it will remain show
//        builder.setCancelable(false);
//
//        // Set the positive button with yes name
//        // OnClickListener method is use of
//        // DialogInterface interface.
//
//        builder
//                .setPositiveButton(
//                        "Yes",
//                        new DialogInterface
//                                .OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog,
//                                                int which)
//                            {
//
//                                // When the user click yes button
//                                // then app will close
//                                finish();
//                            }
//                        });
//
//        // Set the Negative button with No name
//        // OnClickListener method is use
//        // of DialogInterface interface.
//        builder
//                .setNegativeButton(
//                        "No",
//                        new DialogInterface
//                                .OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog,
//                                                int which)
//                            {
//
//                                // If user click no
//                                // then dialog box is canceled.
//                                dialog.cancel();
//                            }
//                        });
//
//        // Create the Alert dialog
//        AlertDialog alertDialog = builder.create();
//        Objects.requireNonNull(alertDialog.getWindow()).setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
//        // Show the Alert Dialog box
//        alertDialog.show();

//        openDialog(this);
//        editText=findViewById(R.id.id_edit_name);
//        timetx=findViewById(R.id.time);
////        datetx=findViewById(R.id.id_date);
//        add=findViewById(R.id.bottomAdd);
//        repeated=findViewById(R.id.id_repeated);
//        cancel=findViewById(R.id.id_cancel);

//        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
//
//        timetx.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showTimePickerDialog();
//            }
//        });
//
//        datetx.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDatePickerDialog();
//            }
//        });
//
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                Intent intent=new Intent(DailogActivity.this, AlarmRciever.class);
//                final PendingIntent pendingIntent=PendingIntent
//                        .getBroadcast(DailogActivity.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
//
//
//                intent.putExtra("notificationId",notificationId);
//                intent.putExtra("Message",editText.getText().toString());
//
//
//                Calendar calendar=Calendar.getInstance();
//
//                calendar.set(Calendar.YEAR,mYear);
//                calendar.set(Calendar.MONTH,mMonth);
//                calendar.set(Calendar.DAY_OF_MONTH,mDay);
//
//                calendar.set(Calendar.HOUR_OF_DAY,currentHour);
//                calendar.set(Calendar.MINUTE,currentMin);
//                calendar.set(Calendar.SECOND,0);
//
//                long alarmStartTime=calendar.getTimeInMillis();
//                alarmManager.set(AlarmManager.RTC_WAKEUP,alarmStartTime,pendingIntent);
//
//                Toast.makeText(DailogActivity.this, "Done", Toast.LENGTH_SHORT).show();
//            }
//
//        });
//
//
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent=new Intent(DailogActivity.this,AlarmRciever.class);
//                final PendingIntent pendingIntent=PendingIntent
//                        .getBroadcast(DailogActivity.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
//
//                alarmManager.cancel(pendingIntent);
//                Toast.makeText(DailogActivity.this, "Alarm cancelled", Toast.LENGTH_SHORT).show();
//            }
//        });


       /* repeated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar=Calendar.getInstance();

                calendar.set(Calendar.YEAR,mYear);
                calendar.set(Calendar.MONTH,mMonth);
                calendar.set(Calendar.DAY_OF_MONTH,mDay);

                calendar.set(Calendar.HOUR_OF_DAY,currentHour);
                calendar.set(Calendar.MINUTE,currentMin);
                calendar.set(Calendar.SECOND,0);
                Intent intent=new Intent(getApplicationContext(),NotificationReciever.class);

                PendingIntent pendingIntent=PendingIntent.getBroadcast(MainActivity.this,100,intent,PendingIntent.FLAG_UPDATE_CURRENT);

            }
        });*/


    }

    private void showDatePickerDialog() {
        Calendar cal = Calendar.getInstance();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(DailogActivity.this, android.R.style.Theme_Holo_Light_Dialog, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                int months= month+1;
                Date date = null;
                try {
                    date = new SimpleDateFormat("dd/MM/yyyy").parse(dayOfMonth + "/" + months + "/" + year);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (date != null) {
                    datetx.setText(new SimpleDateFormat("dd / MM / yyyy").format(date));
                    dateInMilliseconds = date.getTime();
                }
                mYear=year;
                mMonth=month;
                mDay=dayOfMonth;

            }
        }, mYear, mMonth, mDay);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMin = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(DailogActivity.this, android.R.style.Theme_Holo_Light_Dialog, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (hourOfDay>=12){
                    amPm="PM";
                }else {
                    amPm="AM";
                }
                timetx.setText(String.format("%02d:%02d",hourOfDay,minute)+amPm);
                currentHour=hourOfDay;
                currentMin=minute;
            }
        },currentHour,currentMin,false);
        timePickerDialog.show();
    }

    @SuppressLint("ObsoleteSdkInt")
    public static void openDialog(Context context){

        AlertDialog.Builder builder1=new AlertDialog.Builder(context);
        builder1.setTitle("TRIP TO DO");
        builder1.setMessage("It's time for your  trip");
        builder1.setCancelable(false);
        builder1.setPositiveButton("START", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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
                NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
                notificationHelper.getManager().notify(1, nb.build());
            }
        });

        AlertDialog dialog=builder1.create();
        if(dialog.getWindow()!=null){
            int type;
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
                type= WindowManager.LayoutParams.TYPE_TOAST;
            }
            else{
                type= WindowManager.LayoutParams.TYPE_PHONE;
            }
            dialog.getWindow().setType(type);
            Objects.requireNonNull(dialog.getWindow()).setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
            dialog.show();
        }

    }
}