/*

package com.example.saes3.Util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.saes3.R;

public class notif {
    private static final String CHANNEL_ID ="channelNotif";
    NotificationCompat.Builder builder;
    private Context context;
    public void creaNotif(Context context) {
        NotificationCompat.Builder builder  = new NotificationCompat.Builder(context,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.logo_app);
        builder.setContentTitle("Vegetabilis Auditor");
        builder.setContentText("yo");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }
    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

// notificationId is a unique int for each notification that you must define
notificationManager.notify(0, builder.builder());

    public void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Vegetabilis Auditor";
            String description = "c moa";
            //CharSequence name = getString(R.string.channel_name);
           // String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}

*/
