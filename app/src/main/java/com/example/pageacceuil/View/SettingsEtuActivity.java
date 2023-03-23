package com.example.pageacceuil.View;


import static org.apache.poi.sl.draw.geom.GuideIf.Op.max;
import static org.apache.poi.sl.draw.geom.GuideIf.Op.min;
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

public class SettingsEtuActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textAxeLeft;
    private TextView textAxeRight;
    private String choixESP = "";
    private String nameEsp = "";

    private String rightAxisName = "";
    private String leftAxisName = "";
    private EditText max_g;
    private EditText min_g;
    private EditText max_d;
    private EditText min_d;
    private EditText tauxRefresh;

    private Button b_droit;
    private Button b_gauche;
    private Button b_refresh;

    private TextView nomEsp;
    private CheckBox auto_droit;
    private CheckBox auto_gauche;

    private YAxis rightAxis;
    private YAxis leftAxis;

    private int[] valAxeLeft;
    private int[] valAxeRight;
    private SettingsEtuViewModel settingsEtuViewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);

        settingsEtuViewModel = new ViewModelProvider(this).get(SettingsEtuViewModel.class);


        rightAxis = Axe.getInstance().getRightAxis();
        leftAxis = Axe.getInstance().getLeftAxis();

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

        ESP esp = ESP.getInstance();
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("leftAxisName")) {
                this.leftAxisName = (String) intent.getSerializableExtra("leftAxisName");
            }
            if (intent.hasExtra("rightAxisName")) {
                this.rightAxisName = (String) intent.getSerializableExtra("rightAxisName");
            } else {
                System.out.println("impossible récup ESP");
            }
        }
        if (esp.getNomEsp() == null) {
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
        else{
            min_d.setHint("auto");
            max_d.setHint("auto");
        }
        if (leftAxis.isAxisMaxCustom()) {
            auto_gauche.setChecked(false);
            min_g.setHint(leftAxis.getAxisMinimum() + "");
            max_g.setHint(leftAxis.getAxisMaximum() + "");}
            else{
            min_g.setHint("auto");
            max_g.setHint("auto");}


            settingsEtuViewModel.getMoments().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    tauxRefresh.setHint(s);
                }

            });


        tauxRefresh.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && !tauxRefresh.getText().toString().equals("")) {
                    if (settingsEtuViewModel.editTemps((parseInt(tauxRefresh.getText().toString()))) == true) {
                        Toast.makeText(SettingsEtuActivity.this, "Refresh : " + tauxRefresh.getText().toString() + "s,\r\nVous pouvez redémarrer l'ESP", Toast.LENGTH_SHORT).show();
                        tauxRefresh.setText("");
                    } else {
                        Toast.makeText(SettingsEtuActivity.this, "Erreur", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Merci d'entrer une valeur", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

        });
    }

    /**
     *
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent myItent=new Intent();
        setResult(RESULT_OK,myItent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.auto_droit:
                if (auto_droit.isChecked()) {
                    resetAxis(rightAxis);
                    Toast.makeText(getApplicationContext(), "Mode auto activé", Toast.LENGTH_SHORT).show();
                    min_d.setEnabled(false);
                    min_d.setHint("auto");
                    max_d.setEnabled(false);
                    max_d.setHint("auto");
                }
                break;
            case R.id.auto_gauche:
                if (auto_gauche.isChecked()) {
                    resetAxis(leftAxis);
                    Toast.makeText(getApplicationContext(), "Mode auto activé", Toast.LENGTH_SHORT).show();
                    min_g.setEnabled(false);
                    min_g.setHint("auto");
                    max_g.setEnabled(false);
                    max_g.setHint("auto");
                }
                break;
            case R.id.btn_droit:
                if(auto_droit.isChecked()){
                    Toast.makeText(this, "L'axe est bloqué en mode auto", Toast.LENGTH_SHORT).show();
                }
                setAxisMinMax(rightAxis, min_d.getText().toString(), max_d.getText().toString(), "Fait");
                break;
            case R.id.btn_gauche:
                if(auto_gauche.isChecked()){
                    Toast.makeText(this, "L'axe est bloqué en mode auto", Toast.LENGTH_SHORT).show();
                }
                setAxisMinMax(leftAxis, min_g.getText().toString(), max_g.getText().toString(), "Fait");
                break;
            case R.id.btn_refresh:
                refreshSettings();
                break;
        }
    }

    private void resetAxis(YAxis axis) {
        axis.resetAxisMinimum();
        axis.resetAxisMaximum();
    }


    private void setAxisMinMax(YAxis axis, String min, String max, String message) {
        if (((min.trim().length() == 0) || (max.trim().length() == 0)) ) {
            Toast.makeText(getApplicationContext(), "Un champ est vide", Toast.LENGTH_SHORT).show();
        } else {
            if (Float.valueOf(max) > Float.valueOf(min)) {
                axis.setAxisMaximum(Float.valueOf(max));
                axis.setAxisMinimum(Float.valueOf(min));
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Valeurs incorrectes", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void refreshSettings() {
        if (!tauxRefresh.getText().toString().equals("")) {
            if (settingsEtuViewModel.editTemps((parseInt(tauxRefresh.getText().toString())))) {
                Toast.makeText(SettingsEtuActivity.this, "Refresh : " + tauxRefresh.getText().toString() + "s,\r\nVous pouvez redémarrer l'ESP", Toast.LENGTH_SHORT).show();
                tauxRefresh.setText("");
            } else {
                Toast.makeText(SettingsEtuActivity.this, "Erreur", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Merci d'entrer une valeur", Toast.LENGTH_SHORT).show();
        }
    }

}





