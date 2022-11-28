package com.example.pageacceuil;

import android.app.Activity;
import android.app.Dialog;
import android.widget.Button;
import android.widget.TextView;

public class Pop_up extends Dialog {
    private String ch1,ch2;
    private TextView relevé, horaire;
    private Button yesButton,noButton;

    public Pop_up(Activity activity) {
        super(activity, com.google.android.material.R.style.Theme_AppCompat);
        setContentView(R.layout.pop_up_add);

        this.ch1="dd";
        this.ch2="ee";
        this.relevé=findViewById(R.id.textView3);
        this.horaire=findViewById(R.id.textView4);

        this.yesButton=findViewById(R.id.yesButton);
        this.noButton=findViewById(R.id.noButton);

    }
    public void setCh1(String title){this.ch1=title;}

    public void setCh2(String title){this.ch2=title;}

    public Button getYesButton(){ return yesButton;}

    public Button getNoButton(){ return noButton;}

    public void build(){
        show();
        relevé.setText("valeur");
        horaire.setText("horaire");
    }
}
