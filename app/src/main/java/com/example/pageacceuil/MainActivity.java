package com.example.pageacceuil;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class MainActivity extends AppCompatActivity {


    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("SAE_S3_BD/ESP32");


    String choixESP = "";
    String nomESP = "";
    Spinner spinner;
    Button btncoEtu;
    ESP a;
    Button btnCoAdmin;
    HashMap<String, String> ESP;
    ArrayList<String> tabESP;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.SpinnerID);
        btncoEtu = findViewById(R.id.Gobtn);
        btnCoAdmin = findViewById(R.id.adminbtnmain);
        ESP = new HashMap<>();
        tabESP = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, tabESP);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int curseur = 0;
                for (Map.Entry entree : ESP.entrySet()) {
                    if (curseur == position) {
                        if (entree.getValue().toString() != null) {
                            a=new ESP((String) entree.getKey(),(String) entree.getValue());
                        }
                        break;
                    }
                    curseur++;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ESP.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    if (child.child("Nom").exists()) {
                        ESP.put(child.getKey(), String.valueOf(child.child("Nom").getValue()));
                    } else {
                        ESP.putIfAbsent(child.getKey(), null);
                    }

                }
                Iterator iterator = ESP.entrySet().iterator();
                tabESP.clear();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    if (entry.getValue() == null) {
                        tabESP.add((String) entry.getKey());
                    } else {
                        tabESP.add((String) entry.getValue());
                    }
                }
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btncoEtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent graph;
                graph = new Intent(MainActivity.this, GraphPage.class);
//                ESP a=new ESP(choixESP,nomESP);
                ESP esp=new ESP(choixESP,nomESP);
             /*   graph.putExtra("choixESP", choixESP);
                if (!nomESP.equals("")) {
                    graph.putExtra("nomESP", nomESP);
                }*/
                startActivity(graph);
            }
        });

        btnCoAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent admin;
                admin = new Intent(getApplicationContext(), connectadmin.class);
                startActivity(admin);

            }
        });

    }

}