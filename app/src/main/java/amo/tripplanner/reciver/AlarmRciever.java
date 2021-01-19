package amo.tripplanner.reciver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import amo.tripplanner.Helper.NotificationHelper;
import amo.tripplanner.R;
import amo.tripplanner.ui.DailogActivity;
import amo.tripplanner.ui.MainActivity;

public class AlarmRciever extends BroadcastReceiver {
    static String msg;
    Context context;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context1, Intent intent) {

        context = context1;

        int tripId = intent.getIntExtra("TripID", 0);
        String tripName= intent.getStringExtra("TripName");


       /* if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent serviceIntent = new Intent(context, MainActivity.class);

            context.startService(serviceIntent);
        }*/

        Intent i = new Intent(context1, DailogActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.putExtra("TripID",tripId);
            i.putExtra("TripName",tripName);
        }

        context.startActivity(i);


//
//        Intent mainIntent = new Intent(context, MainActivity.class);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mainIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Notification.Builder builder = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
//            builder = new Notification.Builder(context);
//            builder.setSmallIcon(R.drawable.ic_notifications)
//                    .setContentTitle("TRIP TO DO")
//                    .setContentText("msg")
//                    .setWhen(System.currentTimeMillis())
//                    .setOngoing(true)
//                    .setContentIntent(pendingIntent)
//                    .setPriority(Notification.PRIORITY_MAX)
//                    .setDefaults(Notification.DEFAULT_ALL);
//
//            notificationManager.notify(notificationId, builder.build());
//        }

//        DailogActivity.openDialog(context);

//
//        int notificationId=intent.getIntExtra("notificationId",0);
//
//
//
//        Intent i = new Intent(context, DailogActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);
//
//
//        String msg=intent.getStringExtra("Message");
//
////        Intent mainIntent=new Intent(context, MainActivity.class);
////        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,mainIntent,0);
//
//        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        Notification.Builder builder=new Notification.Builder(context);
//        builder.setSmallIcon(R.drawable.ic_account)
//                .setContentTitle("TRIP TO DO")
//                .setContentText(msg)
//                .setWhen(System.currentTimeMillis())
//                .setAutoCancel(true)
//                .setPriority(Notification.PRIORITY_MAX)
//                .setDefaults(Notification.DEFAULT_ALL);
//
//        notificationManager.notify(notificationId,builder.build());
//
//        Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show();
    }

}
