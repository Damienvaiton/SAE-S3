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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingPage extends AppCompatActivity implements View.OnClickListener {

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("SAE_S3_BD/ESP32");
FirebaseAcces databas=FirebaseAcces.getInstance();
    TextView textAxeLeft;
    TextView textAxeRight;
    String choixESP = "";
    String nameEsp = "";

    String rightAxisName = "";
    String leftAxisName = "";
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

        textAxeLeft = findViewById(R.id.textAxeLeft);
        textAxeRight = findViewById(R.id.textAxeRight);

        b_refresh = findViewById(R.id.btn_refresh);
        b_gauche = findViewById(R.id.btn_gauche);
        b_droit = findViewById(R.id.btn_droit);

        auto_droit = findViewById(R.id.auto_droit);
        auto_gauche = findViewById(R.id.auto_gauche);

        nomEsp = findViewById(R.id.nomEsp);

        b_gauche.setOnClickListener(this);
        b_droit.setOnClickListener(this);
        b_refresh.setOnClickListener(this);
        auto_droit.setOnClickListener(this);
        auto_gauche.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("choixESP")) {
                this.choixESP = (String) intent.getSerializableExtra("choixESP");
            }
            if (intent.hasExtra("nomESP")) {
                this.nameEsp = (String) intent.getSerializableExtra("nomESP");
            }
            if (intent.hasExtra("leftAxisName")) {
                this.leftAxisName = (String) intent.getSerializableExtra("leftAxisName");
            }
            if (intent.hasExtra("rightAxisName")) {
                this.rightAxisName = (String) intent.getSerializableExtra("rightAxisName");
            } else {
                System.out.println("impossible récup ESP");
            }
        }
        if (!nameEsp.equals("")) {
            nomEsp.setText(nameEsp);
        } else {
            nomEsp.setText(choixESP);
        }
        if (!leftAxisName.equals("")) {
            textAxeLeft.setText("Colonne " + leftAxisName);
        } else {
            textAxeLeft.setText("Colonne Y gauche");
        }
        if (!rightAxisName.equals("")) {
            textAxeRight.setText("Colonne " + rightAxisName);
        } else {
            textAxeRight.setText("Colonne Y droit");
        }
        if (GraphPage.rightAxis.isAxisMaxCustom()) {
            auto_droit.setChecked(false);
            min_d.setHint(GraphPage.rightAxis.getAxisMinimum() + "");
            max_d.setHint(GraphPage.rightAxis.getAxisMaximum() + "");

        }
        if (GraphPage.leftAxis.isAxisMaxCustom()) {
            auto_gauche.setChecked(false);
            min_g.setHint(GraphPage.leftAxis.getAxisMinimum() + "");
            max_g.setHint(GraphPage.leftAxis.getAxisMaximum() + "");

        }

        myRef.child(choixESP).child("TauxRafraichissement").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String heure = "";
                String minute = "";
                String seconde = "";
                if(snapshot.exists()) {


                if (snapshot.getValue(Long.class) >= 3600000) {
                    heure = (snapshot.getValue(Long.class) / (1000 * 60 * 60) + "h");
                }
                if (snapshot.getValue(Long.class) >= 60000) {
                    minute = (snapshot.getValue(Long.class) % (1000 * 60 * 60)) / (1000 * 60) + "m";
                }
                if (snapshot.getValue(Long.class) >= 1000) {
                    seconde = (snapshot.getValue(Long.class) % (1000 * 60)) / 1000 + "s";
                }
                tauxRefresh.setHint(heure + minute + seconde);
        }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        tauxRefresh.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !tauxRefresh.getText().toString().equals("")) {
                    editTemps((parseInt(tauxRefresh.getText().toString())));
                } else {
                    Toast.makeText(getApplicationContext(), "Merci d'entrer une valeur", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

        });
    }

    public void editTemps(int values) {
        databas.editTemps(choixESP,values);
        Toast.makeText(getApplicationContext(), "Refresh : " + values + "s,\r\nVous pouvez redémarrer l'ESP", Toast.LENGTH_LONG).show();
        tauxRefresh.setText("");

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
                    if (Float.valueOf(max_d.getText().toString()) > Float.valueOf(min_d.getText().toString())) {

                        GraphPage.rightAxis.setAxisMaximum(Float.valueOf(max_d.getText().toString()));
                        GraphPage.rightAxis.setAxisMinimum(Float.valueOf(min_d.getText().toString()));
                        Toast.makeText(getApplicationContext(), "Fait", Toast.LENGTH_SHORT).show();
                        GraphPage.graph.notifyDataSetChanged();
                        GraphPage.graph.invalidate();
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
                        GraphPage.graph.notifyDataSetChanged();
                        GraphPage.graph.invalidate();
                        Toast.makeText(getApplicationContext(), "Fait", Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        Toast.makeText(getApplicationContext(), "Valeurs incorrects", Toast.LENGTH_SHORT).show();
                    }
                }
            case R.id.btn_refresh:
                if (!tauxRefresh.getText().toString().equals("")) {
                    editTemps((parseInt(tauxRefresh.getText().toString())));
                } else {
                    Toast.makeText(getApplicationContext(), "Merci d'entrer une valeur", Toast.LENGTH_SHORT).show();
                }
        }
    }
}



