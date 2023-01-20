package com.example.pageacceuil;


import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class SettingPage extends AppCompatActivity implements View.OnClickListener {

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

String ESP;
    EditText max_g;
    EditText min_g;
    EditText max_d;
    EditText min_d;
    EditText tauxRefresh;

    Button b_droit;
    Button b_gauche;
    Button b_refresh;

TextView nomEsp;
    CheckBox auto_droit;
    CheckBox auto_gauche;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);

        max_g = findViewById(R.id.max_gauche);
        min_g = findViewById(R.id.min_gauche);
        max_d = findViewById(R.id.max_droit);
        min_d = findViewById(R.id.min_droit);
        tauxRefresh = findViewById(R.id.refreshRate);

        b_refresh=findViewById(R.id.btn_refresh);
        b_gauche = findViewById(R.id.btn_gauche);
        b_droit = findViewById(R.id.btn_droit);

        auto_droit = findViewById(R.id.auto_droit);
        auto_gauche = findViewById(R.id.auto_gauche);

        nomEsp=findViewById(R.id.nomEsp);

        b_gauche.setOnClickListener(this);
        b_droit.setOnClickListener(this);
        auto_droit.setOnClickListener(this);
        auto_gauche.setOnClickListener(this);

        Intent intent=getIntent();
        if (intent != null) {
            if (intent.hasExtra("ESP")) {
                this.ESP = (String) intent.getSerializableExtra("ESP");
                System.out.println("ok");
            } else {
                System.out.println("erreur");
            }
        }

        nomEsp.setText(ESP);

        if (GraphPage.rightAxis.isAxisMaxCustom()) {
            auto_droit.setChecked(false);
        }
        if (GraphPage.leftAxis.isAxisMaxCustom()) {
            auto_gauche.setChecked(false);
        }


        tauxRefresh.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    editTemps((parseInt(tauxRefresh.getText().toString())));
                }
                return false;
            }

        });
    }

    public void editTemps(int values) {
        DatabaseReference varTemps = database.getReference("SAE_S3_BD/ESP32/A8:03:2A:EA:EE:CC");
        varTemps.child("TauxRafraichissement").setValue(values);
        tauxRefresh.setHint(values + " s");
        Toast.makeText(getApplicationContext(), "Refresh : " + values + "s", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.auto_droit:
                if (auto_droit.isChecked()) {
                    GraphPage.rightAxis.resetAxisMinimum();
                    GraphPage.rightAxis.resetAxisMaximum();
                    Toast.makeText(getApplicationContext(), "Mode auto activé", Toast.LENGTH_SHORT).show();
                    //Griser case pour le manuel
                    break;
                }
            case R.id.auto_gauche:
                if (auto_gauche.isChecked()) {
                    GraphPage.leftAxis.resetAxisMinimum();
                    GraphPage.leftAxis.resetAxisMaximum();
                    Toast.makeText(getApplicationContext(), "Mode auto activé", Toast.LENGTH_SHORT).show();
                    //Griser case pour le manuel
                    break;
                }

            case R.id.btn_droit:
                if (((min_d.getText().toString().trim().length() == 0) || (max_d.getText().toString().trim().length() == 0)) || (auto_droit.isChecked())) {
                    Toast.makeText(getApplicationContext(), "Un champ est vide", Toast.LENGTH_SHORT).show();
                } else {

                    System.out.println(min_g.getText());
                    System.out.println(min_g.getText());
                    // A test
                    if (Float.valueOf(max_d.getText().toString()) > Float.valueOf(min_d.getText().toString())) {

                        GraphPage.rightAxis.setAxisMaximum(Float.valueOf(max_d.getText().toString()));
                        GraphPage.rightAxis.setAxisMinimum(Float.valueOf(min_d.getText().toString()));
                        Toast.makeText(getApplicationContext(), "Fait", Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        Toast.makeText(getApplicationContext(), "Valeurs incorrects", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_gauche:

                if (((min_g.getText().toString().trim().length() == 0) || (max_g.getText().toString().trim().length() == 0)) || (auto_gauche.isChecked())) {
                    Toast.makeText(getApplicationContext(), "Un champ est vide", Toast.LENGTH_SHORT).show();
                } else {
                    if (Float.valueOf(max_g.getText().toString()) > Float.valueOf(min_g.getText().toString())) {

                        GraphPage.leftAxis.setAxisMaximum(Float.valueOf(max_g.getText().toString()));
                        GraphPage.leftAxis.setAxisMinimum(Float.valueOf(min_g.getText().toString()));
                        Toast.makeText(getApplicationContext(), "Fait", Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        Toast.makeText(getApplicationContext(), "Valeurs incorrects", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }
}



