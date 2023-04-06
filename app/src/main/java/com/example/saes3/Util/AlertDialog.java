package com.example.saes3.Util;


import com.example.saes3.AppApplication;
import com.example.saes3.Model.ListData;


public class AlertDialog {

    private static AlertDialog instance;

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
        alertDialog.setPositiveButton("Oui", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    public void emptyESP(){
        android.app.AlertDialog.Builder alertDialog=new android.app.AlertDialog.Builder(AppApplication.getCurrentActivity());
        alertDialog.setMessage("Il semblerait que l'ESP ne contienne aucune données, l'avez vous brancher?");
        alertDialog.setPositiveButton("Oui", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    public void errorFirebase() {
        android.app.AlertDialog.Builder alertDialog=new android.app.AlertDialog.Builder(AppApplication.getCurrentActivity());
        alertDialog.setMessage("Erreur lors de l'enregistrement Firebase");
        alertDialog.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }
}
