package com.example.pageacceuil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PageGroupe extends AppCompatActivity {


    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("SAE_S3_BD/ESP32");
    DatabaseReference myRefGroupe = database.getReference("SAE_S3_BD/Groupe");

    String choixESP = "";
    String nomESP = "";
    ArrayList<String> tablESP;

    ArrayList<String> tablGroupe;

    GroupeESP groupe;



    ESP a;

    HashMap<String, String> ESP;

    ArrayList<String> tabESP;





    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_management_groupe);
        tablESP = new ArrayList<>();
        tablGroupe = new ArrayList<>();
        ESP = new HashMap<>();
        tabESP = new ArrayList<>();
         RecyclerView recyclerView = findViewById(R.id.recyclerViewESP);

        DataAdapterGroupe dataAdapter = new DataAdapterGroupe(getApplicationContext());

        recyclerView.setAdapter(dataAdapter);



        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Spinner spinner = findViewById(R.id.Esplist);
        Spinner spinnerGroupe = findViewById(R.id.Snipperchoix);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(PageGroupe.this, android.R.layout.simple_spinner_dropdown_item, tablESP);
        ArrayAdapter<String> adapterGroupe = new ArrayAdapter<>(PageGroupe.this, android.R.layout.simple_spinner_dropdown_item, tablGroupe);
        spinner.setAdapter(adapter);
        spinnerGroupe.setAdapter(adapterGroupe);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int curseur = 0;
                for (Map.Entry entree : ESP.entrySet()) {
                    if (curseur == position) {
                        choixESP = (String) entree.getKey();
                        if (entree.getValue().toString() != null) {
                            nomESP = (String) entree.getValue();
                            a = new ESP("yo", "yi");
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

        spinnerGroupe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int curseur = 0;
                for (Map.Entry entree : ESP.entrySet()) {
                    if (curseur == position) {
                        choixESP = (String) entree.getKey();
                        if (entree.getValue().toString() != null) {
                            nomESP = (String) entree.getValue();
                            a = new ESP("yo", "yi");
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
                tablESP.clear();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    if (entry.getValue() == null) {
                        tablESP.add((String) entry.getKey());
                    } else {
                        tablESP.add((String) entry.getValue());
                    }
                }
                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        myRefGroupe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tabESP.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                  tabESP.add(child.getKey());

                }
                Iterator iterator = tabESP.iterator();
                tablGroupe.clear();
                while (iterator.hasNext()) {
tablGroupe.add((String) iterator.next());


                }

                adapterGroupe.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }
}
