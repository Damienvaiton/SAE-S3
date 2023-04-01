package com.example.saes3.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ActionReceive extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"recieved", Toast.LENGTH_SHORT).show();
            System.out.println("yooooooooooooooooooooooooooooooooooo notif");
        //This is used to close the notification tray
        //pour fermer la notif, crash
        //Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        //context.sendBroadcast(it);
    }
}
