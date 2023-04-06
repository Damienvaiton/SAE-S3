package com.example.saes3.Util;

import android.content.DialogInterface;

import com.example.saes3.AppApplication;
import com.example.saes3.Model.ListData;

public class AlertDialog {

    public static AlertDialog instance;

    public static AlertDialog getInstance() {

        AlertDialog result = instance;
        if (result != null) {
            return result;
        }
        synchronized (ListData.class) {
            if (instance == null) {
                instance = new AlertDialog();
            }
            return instance;
        }
    }
    public void lostESP(){
        android.app.AlertDialog.Builder alertDialog=new android.app.AlertDialog.Builder(AppApplication.getCurrentActivity());
        alertDialog.setMessage("Il semblerait que l'ESP que le signal de l'ESP ai été perdu, vérifié son alimentation électrique");
        alertDialog.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void emptyESP(){
        android.app.AlertDialog.Builder alertDialog=new android.app.AlertDialog.Builder(AppApplication.getCurrentActivity());
        alertDialog.setMessage("Il semblerait que l'ESP ne contienne aucune données, l'avez vous brancher?");
        alertDialog.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
