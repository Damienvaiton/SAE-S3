package com.example.pageacceuil;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SettingPage extends AppCompatActivity {

    EditText max_g;
    EditText min_g;
    EditText max_d;
    EditText min_d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);

        max_g= findViewById(R.id.max_gauche);
        min_g= findViewById(R.id.min_gauche);
        max_d= findViewById(R.id.max_droit);
        min_d= findViewById(R.id.min_droit);
    }
}