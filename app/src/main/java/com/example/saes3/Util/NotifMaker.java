package com.example.saes3.Util;

import static com.example.saes3.AppApplication.getAppContext;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.saes3.Model.Data;
import com.example.saes3.Model.ESP;
import com.example.saes3.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotifMaker extends Service {
    private static final String CHANNEL_ID = "channelNotif";
    NotificationManager notificationManager;

    private static NotifMaker instance;

    private DatabaseReference myRef;

    public static NotifMaker getInstance() {
        NotifMaker result = instance;
        if (result != null) {
            return result;
        }
        synchronized (NotifMaker.class) {
            if (instance == null) {
                instance = new NotifMaker();
            }
            return instance;
        }
    }

    /*public notif() {
        this.context = AppApplication.getAppContext();
        builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        notificationManager = NotificationManager.from(context);
        createNotificationChannel();
    }
*/

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Référence de la base de données Firebase à écouter pour les mises à jour
        myRef = FirebaseDatabase.getInstance().getReference("SAE_S3_BD").child(ESP.getInstance().getMacEsp()).child("Mesure");

        // Création d'un écouteur de valeurs pour la référence Firebase
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Data newData = dataSnapshot.getValue(Data.class);
                updateNotification(newData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        };
        myRef.addValueEventListener(valueEventListener);

        notificationManager = (NotificationManager) getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return Service.START_NOT_STICKY;
        }
        Notification notification = creaNotif();
        notificationManager.notify(1, notification);

        // Service démarré en mode START_STICKY pour qu'il continue à s'exécuter en arrière-plan
        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void updateNotification(Data data) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getAppContext());
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getAppContext(), CHANNEL_ID);

        builder.setContentText(data.toString());
builder.setContentTitle(data.getTemps());
        builder.setSmallIcon(R.drawable.logo_app);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_LOW);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
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

    public Notification creaNotif() {
        Intent intentAction = new Intent(getAppContext(),ActionReceive.class);
//This is optional if you have more than one buttons and want to differentiate between two
       // intentAction.putExtra("action","actionName");
        PendingIntent pIntentlogin = PendingIntent.getBroadcast(getAppContext(), 1, intentAction, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getAppContext(), CHANNEL_ID);

        builder.setSmallIcon(R.drawable.logo_app);
        builder.setPriority(NotificationCompat.PRIORITY_LOW);
        builder.addAction(R.drawable.logo_app, "Désactiver", pIntentlogin);
        builder.setOngoing(true);
        builder.setSound(null);
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        builder.setFullScreenIntent(null, false);
        return builder.build();
    }

    private ValueEventListener valueEventListener;

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
            NotificationManager notificationManager = getAppContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
