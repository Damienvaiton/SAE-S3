package com.example.pageacceuil;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.FirebaseDatabase;

public class Pop_up  extends Dialog implements View.OnClickListener{

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private EditText valeur, horaire;
    private Button yesButton, noButton;

    public Pop_up(Activity activity) {
        super(activity, com.google.android.material.R.style.Theme_AppCompat_Dialog);
        setContentView(R.layout.pop_up_add);

        this.valeur = (EditText) findViewById(R.id.pop_txt_value);
        this.horaire = (EditText) findViewById(R.id.pop_txt_time);

        this.yesButton = findViewById(R.id.yesButton);
        this.noButton = findViewById(R.id.noButton);

    }


    public Button getYesButton() {
        return yesButton;
    }

    public Button getNoButton() {
        return noButton;
    }

    public String getHoraire() {
        return horaire.getText().toString();
    }

    public float getValue() {
        return Float.valueOf(valeur.getText().toString());
    }

    public void build() {
        show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.yesButton:
                System.out.println( getValue());
                //Requete bd
                break;
            case R.id.noButton:
                v.dismiss();
                //Faire quitter la popup;
                break;

        }

    }


}




