package com.example.pageacceuil;

import static com.example.pageacceuil.GraphPage.rightAxis;

import static java.lang.Integer.parseInt;

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

public class SettingPage extends AppCompatActivity implements View.OnClickListener {

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    EditText max_g;
    EditText min_g;
    EditText max_d;
    EditText min_d;
    EditText tauxRefresh;

    Button b_droit;
    Button b_gauche;


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
        tauxRefresh=findViewById(R.id.refreshRate);
        b_droit = findViewById(R.id.btn_droit);

        auto_droit=findViewById(R.id.auto_droit);
        auto_gauche=findViewById(R.id.auto_gauche);

       b_gauche.setOnClickListener(this);
        b_droit.setOnClickListener(this);





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
            tauxRefresh.setHint(values+" s");
            Toast.makeText(getApplicationContext(), "Refresh : " + values+"s", Toast.LENGTH_SHORT).show();


        }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_droit:
                if (((min_d.getText().toString().trim().length() == 0)||(max_d.getText().toString().trim().length() == 0))||(auto_droit.isChecked())){
                    Toast.makeText(getApplicationContext(),"Un champ est vide",Toast.LENGTH_SHORT).show();    }
            else{
                    System.out.println(min_g.getText());
                System.out.println(min_g.getText());
                    // A test
                rightAxis.setAxisMaximum(Float.valueOf(max_d.getText().toString()));
                rightAxis.setAxisMinimum(Float.valueOf(min_d.getText().toString()));
                Toast.makeText(getApplicationContext(),"Fait",Toast.LENGTH_SHORT).show();    }
                break;
            case R.id.btn_gauche:
                if (((min_g.getText().toString().trim().length() == 0)||(max_g.getText().toString().trim().length() == 0))||(auto_gauche.isChecked())){
                    Toast.makeText(getApplicationContext(),"Un champ est vide",Toast.LENGTH_SHORT).show();    }
                else{
                    System.out.println(min_g.getText());
                System.out.println(min_g.getText());
                // A test
                    rightAxis.setAxisMaximum(Float.valueOf(max_g.getText().toString()));
                    rightAxis.setAxisMinimum(Float.valueOf(min_g.getText().toString()));
        Toast.makeText(getApplicationContext(),"Fait",Toast.LENGTH_SHORT).show();    }
                break;

        }
    }
}



