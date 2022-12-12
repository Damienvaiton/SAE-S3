package com.example.pageacceuil;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class Pop_up  extends Dialog implements View.OnClickListener{

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    private final EditText valeur;
    private final Button yesButton;
    private final Button noButton;

    public Pop_up(Activity activity) {
        super(activity, com.google.android.material.R.style.Theme_AppCompat_Dialog);
        setContentView(R.layout.pop_up_add);

        this.valeur = findViewById(R.id.pop_txt_value);

        this.yesButton = findViewById(R.id.yesButton);
        this.noButton = findViewById(R.id.noButton);

    }


    public Button getYesButton() {
        return yesButton;
    }

    public Button getNoButton() {
        return noButton;
    }



    public float getValue() {
        return Float.valueOf(valeur.getText().toString());
    }


    public void build() {
        show();
    }

    public void build(String txt) {
        valeur.setText("Êtes-vous vraiment sûr de vouloir quitter? Toutes les données actuelles seront supprimées");
        show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.yesButton:
                System.out.println("mo");
                Toast.makeText(getOwnerActivity(),"ok", Toast.LENGTH_SHORT).show();
                //Requete bd
                break;
            case R.id.noButton:

                //Faire quitter la popup;
                break;

        }

    }


}




