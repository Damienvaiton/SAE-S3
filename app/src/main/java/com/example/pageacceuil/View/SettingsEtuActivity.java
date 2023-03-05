package com.example.pageacceuil.View;


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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pageacceuil.Model.Axe;
import com.example.pageacceuil.Model.ESP;
import com.example.pageacceuil.R;
import com.example.pageacceuil.ViewModel.SettingsEtuViewModel;
import com.github.mikephil.charting.components.YAxis;
import com.google.firebase.database.FirebaseDatabase;

import com.example.pageacceuil.Model.FirebaseAccess;

public class SettingsEtuActivity extends AppCompatActivity implements View.OnClickListener {

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
FirebaseAccess databas= FirebaseAccess.getInstance();
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

    YAxis rightAxis;
    YAxis leftAxis;

    SettingsEtuViewModel settingsEtuViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);

        settingsEtuViewModel =new ViewModelProvider(this).get(SettingsEtuViewModel.class);


        rightAxis= Axe.getInstance().getRightAxis();
        leftAxis=Axe.getInstance().getLeftAxis();

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

        ESP esp=ESP.getInstance();
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
        if (!esp.getNomEsp().equals("")) {
            nomEsp.setText(esp.getNomEsp());
        } else {
            nomEsp.setText(esp.getMacEsp());
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
        if (rightAxis.isAxisMaxCustom()) {
            auto_droit.setChecked(false);
            min_d.setHint(rightAxis.getAxisMinimum() + "");
            max_d.setHint(rightAxis.getAxisMaximum() + "");

        }
        if (leftAxis.isAxisMaxCustom()) {
            auto_gauche.setChecked(false);
            min_g.setHint(leftAxis.getAxisMinimum() + "");
            max_g.setHint(leftAxis.getAxisMaximum() + "");


            settingsEtuViewModel.getMoments().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    tauxRefresh.setHint(s);
                }

            });
        }

        tauxRefresh.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !tauxRefresh.getText().toString().equals("")) {
                    if(settingsEtuViewModel.editTemps((parseInt(tauxRefresh.getText().toString())))==true){
                        tauxRefresh.setText("");
                        Toast.makeText(SettingsEtuActivity.this, "Refresh : " + tauxRefresh.getText().toString() + "s,\r\nVous pouvez redémarrer l'ESP", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(SettingsEtuActivity.this, "Erreur", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Merci d'entrer une valeur", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

        });
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.auto_droit:
                if (auto_droit.isChecked()) {
                    rightAxis.resetAxisMinimum();
                    rightAxis.resetAxisMaximum();
                    Toast.makeText(getApplicationContext(), "Mode auto activé", Toast.LENGTH_SHORT).show();
                    //Griser case pour le manuel
                    break;
                }
            case R.id.auto_gauche:
                if (auto_gauche.isChecked()) {
                    leftAxis.resetAxisMinimum();
                    leftAxis.resetAxisMaximum();
                    Toast.makeText(getApplicationContext(), "Mode auto activé", Toast.LENGTH_SHORT).show();
                    //Griser case pour le manuel
                    break;
                }

            case R.id.btn_droit:
                if (((min_d.getText().toString().trim().length() == 0) || (max_d.getText().toString().trim().length() == 0)) || (auto_droit.isChecked())) {
                    Toast.makeText(getApplicationContext(), "Un champ est vide", Toast.LENGTH_SHORT).show();
                } else {
                    if (Float.valueOf(max_d.getText().toString()) > Float.valueOf(min_d.getText().toString())) {

                        rightAxis.setAxisMaximum(Float.valueOf(max_d.getText().toString()));
                        rightAxis.setAxisMinimum(Float.valueOf(min_d.getText().toString()));
                        Toast.makeText(getApplicationContext(), "Fait", Toast.LENGTH_SHORT).show();
                        GraphiqueActivity.graph.notifyDataSetChanged();
                        GraphiqueActivity.graph.invalidate();
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
                    if (Float.parseFloat(max_g.getText().toString()) > Float.parseFloat(min_g.getText().toString())) {
                        leftAxis.setAxisMaximum(Float.parseFloat(max_g.getText().toString()));
                        leftAxis.setAxisMinimum(Float.parseFloat(min_g.getText().toString()));
                        GraphiqueActivity.graph.notifyDataSetChanged();
                        GraphiqueActivity.graph.invalidate();
                        Toast.makeText(getApplicationContext(), "Fait", Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        Toast.makeText(getApplicationContext(), "Valeurs incorrects", Toast.LENGTH_SHORT).show();
                    }
                }
            case R.id.btn_refresh:
                if (!tauxRefresh.getText().toString().equals("")) {
                    if(settingsEtuViewModel.editTemps((parseInt(tauxRefresh.getText().toString())))){
                        tauxRefresh.setText("");
                        Toast.makeText(SettingsEtuActivity.this, "Refresh : " + tauxRefresh.getText().toString() + "s,\r\nVous pouvez redémarrer l'ESP", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(SettingsEtuActivity.this, "Erreur", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Merci d'entrer une valeur", Toast.LENGTH_SHORT).show();
                }
        }
    }
}



