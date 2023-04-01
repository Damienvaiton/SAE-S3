package com.example.saes3.Util;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.ObservableArrayList;

import com.example.saes3.AppApplication;
import com.example.saes3.Model.Data;
import com.example.saes3.Model.ListData;
import com.example.saes3.R;

public class notif {
    private static final String CHANNEL_ID = "channelNotif";
    NotificationCompat.Builder builder;
    NotificationManagerCompat notificationManager;
Context context;
    private static notif instance;


    public static notif getInstance() {

        notif result = instance;
        if (result != null) {
            return result;
        }
        synchronized (notif.class) {
            if (instance == null) {
                instance = new notif();
            }
            return instance;
        }
    }


    public notif() {
        this.context= AppApplication.getAppContext();
        builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        notificationManager = NotificationManagerCompat.from(context);
        createNotificationChannel();
    }

    public void creaNotif(Data data) {
        builder.setSmallIcon(R.drawable.logo_app);
        builder.setContentTitle("Degré | Humi  |    O2    |    CO2    | Lumière");
        builder.setContentText(data.toString());
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1, builder.build());

    }


// notificationId is a unique int for each notification that you must define


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Vegetabilis Auditor";
            String description = "c moa";
            //CharSequence name = getString(R.string.channel_name);
           // String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
