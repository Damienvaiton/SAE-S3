package com.example.pageacceuil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class pageSettingAdmin extends AppCompatActivity implements View.OnClickListener {

    Spinner spinner;
    Button rename;
    Button delete;
    EditText refresh;
    Button grouper;
    Button reini;
    TextView idEsp;
    HashMap<String,String> ESP;
    ArrayList<String> tabESP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_setting_admin);

        rename= findViewById(R.id.rennoméA);
        delete= findViewById(R.id.suppA);
        refresh= findViewById(R.id.refreshA);
        grouper= findViewById(R.id.grouperA);
        reini= findViewById(R.id.reiniA);
        idEsp= findViewById(R.id.idEsp);

        Intent intent = getIntent();
        if (intent != null) {


                tabESP= (ArrayList<String>) intent.getSerializableExtra("listeESP");
                ESP = (HashMap<String, String>) intent.getSerializableExtra("hashmapEsp");

                System.out.println("ok");
            } else {
                System.out.println("erreudsvr");

            }

/*
         idEsp.setText(nameESP+"");*/

        spinner=findViewById(R.id.spinnerAdmin);
        ArrayAdapter<String> adapterA = new ArrayAdapter<>(pageSettingAdmin.this, android.R.layout.simple_spinner_dropdown_item,tabESP);
        spinner.setAdapter(adapterA);
       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int curseur=0;
                for (Map.Entry entree : ESP.entrySet()) {

                    /*if (curseur==position){
                        ChoixESP=(String)entree.getKey();
                        System.out.println((String)entree.getKey());
                    } curseur++;*/
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

         }


        });
    }

@Override
    public void onClick(View v) {
    switch (v.getId()) {
    }
}
}