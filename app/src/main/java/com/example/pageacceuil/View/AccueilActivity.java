package com.example.pageacceuil.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pageacceuil.Model.ESP;
import com.example.pageacceuil.R;
import com.example.pageacceuil.ViewModel.AccueilViewModel;
import com.example.pageacceuil.ViewModel.ConnectAdminViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class AccueilActivity extends AppCompatActivity {


    private AccueilViewModel accueilViewModel = null;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("SAE_S3_BD/ESP32");

    private Spinner spinner;
    private int position;
    private Button btncoEtu;
    private ESP currentESP;
    private Button btnCoAdmin;
    //HashMap<String, String> ESP;
    private ArrayList<String> tabESP;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accueilViewModel = new ViewModelProvider(this).get(AccueilViewModel.class);
        spinner = findViewById(R.id.SpinnerID);
        btncoEtu = findViewById(R.id.Gobtn);
        btnCoAdmin = findViewById(R.id.adminbtnmain);
        tabESP = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(AccueilActivity.this, android.R.layout.simple_spinner_dropdown_item, tabESP);
        spinner.setAdapter(adapter);


        //Observeur
    accueilViewModel.getESP().observe(this, new Observer<ArrayList<String>>() {
    @Override
    public void onChanged(ArrayList<String> strings) {
        tabESP.clear();
        for (String ESP : strings) {
            tabESP.add(ESP.toString());
        }
        adapter.notifyDataSetChanged();
    }
});

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                position=pos;
        }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btncoEtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent graph;
                accueilViewModel.creaESP(position);
                graph = new Intent(AccueilActivity.this, GraphiqueActivity.class);
                startActivity(graph);
            }
        });

        btnCoAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent admin;
                admin = new Intent(getApplicationContext(), ConnecAdminActivity.class);
                startActivity(admin);

            }
        });

    }

}