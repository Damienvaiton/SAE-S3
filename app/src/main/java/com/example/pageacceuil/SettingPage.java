package com.example.pageacceuil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

    Button b_droit;
    Button b_gauche;
    Button reset_bd;

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

        b_droit = findViewById(R.id.btn_droit);
        b_gauche = findViewById(R.id.btn_gauche);
        reset_bd=findViewById(R.id.delete_bd);

        reset_bd=findViewById(R.id.delete_bd);
        auto_droit=findViewById(R.id.auto_droit);
        auto_gauche=findViewById(R.id.auto_gauche);

        reset_bd.setOnClickListener(this);
       b_gauche.setOnClickListener(this);
        b_droit.setOnClickListener(this);


            }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.delete_bd:
                Pop_up customPopup=new Pop_up(this);
                customPopup.build("Sûr?");
                customPopup.getYesButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                    DatabaseReference delete = database.getReference("SAE_S3_BD/ESP32/A8:03:2A:EA:EE:CC/Mesure");
                    DatabaseReference deleteNumber = database.getReference("SAE_S3_BD/ESP32/A8:03:2A:EA:EE:CC/MesureNumber");
                deleteNumber.removeValue();
                delete.removeValue();
                        customPopup.dismiss();}});
                customPopup.getNoButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customPopup.dismiss();
                    }
                });
                break;

            case R.id.btn_droit:
                if (((min_d.getText().toString().trim().length() == 0)||(max_d.getText().toString().trim().length() == 0))||(auto_droit.isChecked())){
                    Toast.makeText(getApplicationContext(),"Un champ est vide",Toast.LENGTH_SHORT).show();    }
            else{
                    System.out.println(min_g.getText());
                System.out.println(min_g.getText());
                Toast.makeText(getApplicationContext(),"Fait",Toast.LENGTH_SHORT).show();    }
                break;
            case R.id.btn_gauche:
                if (((min_g.getText().toString().trim().length() == 0)||(max_g.getText().toString().trim().length() == 0))||(auto_gauche.isChecked())){
                    Toast.makeText(getApplicationContext(),"Un champ est vide",Toast.LENGTH_SHORT).show();    }
                else{
                    System.out.println(min_g.getText());
                System.out.println(min_g.getText());
        Toast.makeText(getApplicationContext(),"Fait",Toast.LENGTH_SHORT).show();    }
                break;

        }
    }
}



