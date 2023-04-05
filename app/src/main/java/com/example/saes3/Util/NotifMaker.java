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
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.saes3.Model.Data;
import com.example.saes3.Model.ESP;
import com.example.saes3.Model.FirebaseAccess;
import com.example.saes3.Model.ListData;
import com.example.saes3.R;
import com.example.saes3.View.GraphiqueActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NotifMaker extends Service {
    private static final String CHANNEL_ID = "channelNotif";
    NotificationManager notificationManager;

    private static NotifMaker instance;
    private ChildEventListener realtimeDataListener;

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


        myRef = FirebaseDatabase.getInstance().getReference("SAE_S3_BD").child("ESP32").child("A8:03:2A:EA:EE:CC").child("Mesure");

        realtimeDataListener = new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // TODO document why this method is empty
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getChildrenCount() == 6) {
                    updateNotification(snapshot.getValue(Data.class));
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // TODO document why this method is empty
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // TODO document why this method is empty
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Erreur",error.toString());}
        };
        myRef.child("SAE_S3_BD").child("ESP32").child("A8:03:2A:EA:EE:CC").child("Mesure").addChildEventListener(realtimeDataListener);


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

        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void updateNotification(Data data) {
        System.out.println(data.toString()+"ddd");
        Intent notifIntent=new Intent(this,
                GraphiqueActivity.class);
        PendingIntent pIntentlogin = PendingIntent.getBroadcast(getAppContext(), 1, notifIntent, PendingIntent.FLAG_IMMUTABLE);

        Notification notification=new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("Vegetabilis Auditor")

                .setContentText(data.toString())
                .setSmallIcon(R.drawable.logo_app)
                .setContentIntent(pIntentlogin)
                .setOngoing(true)
                .setSound(null)
                .addAction(R.drawable.logo_app, "Désactiver", pIntentlogin)
                .build();

        startForeground(1,notification);
        /*builder.setPriority(NotificationCompat.PRIORITY_LOW);
        builder.addAction(R.drawable.logo_app, "Désactiver", pIntentlogin);
        builder.setOngoing(true);
        builder.setSound(null);
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        builder.setFullScreenIntent(null, false);
        return builder.build();*/

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




}