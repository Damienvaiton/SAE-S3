package com.example.saes3.Util;

        import static com.example.saes3.View.GraphiqueActivity.CHANNEL_ID;

        import android.Manifest;
        import android.app.NotificationChannel;
        import android.app.NotificationManager;
        import android.app.PendingIntent;
        import android.content.Context;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.os.Build;

        import androidx.core.app.ActivityCompat;
        import androidx.core.app.NotificationCompat;
        import androidx.core.app.NotificationManagerCompat;

        import com.example.saes3.AppApplication;
        import com.example.saes3.Model.Data;
        import com.example.saes3.R;

public class NotifMarker {
    NotificationCompat.Builder builder;
    NotificationManagerCompat notificationManager;
    Context context;
    private static NotifMarker instance;


    public static NotifMarker getInstance() {

        NotifMarker result = instance;
        if (result != null) {
            return result;
        }
        synchronized (NotifMarker.class) {
            if (instance == null) {
                instance = new NotifMarker();
            }
            return instance;
        }
    }


  /*  public NotifMarker() {
        this.context= AppApplication.getAppContext();
        builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        notificationManager = NotificationManagerCompat.from(context);
    }

    public void creaNotif(Data data) {
        Intent intentAction = new Intent(context,ActionReceive.class);
//This is optional if you have more than one buttons and want to differentiate between two
        // intentAction.putExtra("action","actionName");
        PendingIntent pIntentlogin = PendingIntent.getBroadcast(context, 1, intentAction, PendingIntent.FLAG_IMMUTABLE);

        builder.setSmallIcon(R.drawable.logo_app);
        builder.setContentTitle("Degré | Humi  |    O2    |    CO2    | Lumière");
        builder.setContentText(data.toString());
        builder.setPriority(NotificationCompat.PRIORITY_LOW);
        builder.addAction(R.drawable.logo_app, "Désactiver", pIntentlogin);
        builder.setOngoing(true);
        builder.setSound(null);
        builder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

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
*/

// notificationId is a unique int for each notification that you must define




}