package com.example.pageacceuil;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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

public class pageSettingAdmin extends AppCompatActivity implements View.OnClickListener {


    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("SAE_S3_BD");


    RecyclerView recyclerView;

    ValueEventListener valueEventListenerTemps;

    ValueEventListener valueEventListenerDate;
    Spinner spinner;
    Button valiRefresh;
    Button rename;

    Button delete;
    EditText refresh;
    Button grouper;
    Button reini;
    TextView idEsp;
    ListData dataESP;
    String choixESP;

    String oldChoixESP = "";
    HashMap<String, String> ESP;
    ArrayAdapter<String> adapter;
    ArrayList<String> tabESP;
    ArrayList<String> Groupe;


    DataAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_setting_admin);

        idEsp = findViewById(R.id.selectedEsp);
        rename = findViewById(R.id.rennoméA);
        delete = findViewById(R.id.suppA);
        refresh = findViewById(R.id.refreshA);
        grouper = findViewById(R.id.grouperA);
        reini = findViewById(R.id.reiniA);
        spinner = findViewById(R.id.spinnerAdmin);
        valiRefresh = findViewById(R.id.valiRefresh);
        recyclerView = findViewById(R.id.recyclerViewAdmin);


        valiRefresh.setOnClickListener(this);
        rename.setOnClickListener(this);
        delete.setOnClickListener(this);
        grouper.setOnClickListener(this);
        reini.setOnClickListener(this);
        ESP = new HashMap<>();
        tabESP = new ArrayList<>();
        Groupe = new ArrayList<>();

        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, tabESP);
        spinner.setAdapter(adapter);

        dataESP = ListData.getInstance();

        dataAdapter = new DataAdapter(getApplicationContext());
        recyclerView.setAdapter(dataAdapter);
        recyclerView.setLayoutManager((new LinearLayoutManager((this))));

        AlertDialog.Builder pop = new AlertDialog.Builder(pageSettingAdmin.this);
        pop.setMessage("Assurez-vous qu'avant toute modification l'ESP est éteint.");
        pop.setPositiveButton("Compris", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Prêt", Toast.LENGTH_SHORT).show();
                dialog.cancel();

            }
        });
        pop.show();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dataESP.deleteAllData();
                if (position <= ESP.size()) {
                    int curseur = 0;
                    for (Map.Entry entree : ESP.entrySet()) {

                        if (curseur == position) {
                            if (choixESP != null) {
                                if (!choixESP.equals("")) {
                                    oldChoixESP = choixESP;
                                }
                            }
                            choixESP = (String) entree.getKey();
                            idEsp.setText(choixESP);
                            actu();
                            break;
                        }
                        curseur++;
                    }
                } else {
                    choixESP = tabESP.get(position);
                    idEsp.setText("Groupe : " + choixESP);
                    System.out.println(choixESP);
                    myRef.child(choixESP).child("ESP").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Groupe.add(snapshot.getValue(String.class));
                            System.out.println(snapshot.getValue(String.class)+"yofreot");
                            for (String a : Groupe) {
                                System.out.println(a+"eeEEEEEe");
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    myRef.child(choixESP).child("ESP").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Groupe.add(snapshot.getValue(String.class));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




      /*  myRef.child("Groupe").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        String nomTemp = child.getValue(String.class);
                        Groupe.add(child.getValue(String.class));
                        for (DataSnapshot enfant : child.getChildren()) {
                            enfant.add(enfant.getChildren()) {
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void onCancelled (@NonNull DatabaseError error){

        }
    })*/

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ESP.clear();
                if (snapshot.child("ESP32").exists() && snapshot.child("ESP32") != null) {
                    for (DataSnapshot child : snapshot.child("ESP32").getChildren()) {
                        if (child.child("Nom").exists()) {
                            ESP.put(child.getKey(), String.valueOf(child.child("Nom").getValue()));
                        } else {
                            ESP.putIfAbsent(child.getKey(), null);
                        }
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
                if (snapshot.child("Groupe").exists() && snapshot.child("Groupe") != null) {
                    for (DataSnapshot childgroupe : snapshot.child("Groupe").getChildren()) {
                        if (childgroupe.child("nom").exists()) {
                            tabESP.add(childgroupe.child("nom").getValue().toString());
                        } else {
                            tabESP.add(childgroupe.getValue().toString());
                        }

                    }

                    adapter.notifyDataSetChanged();
                       /* int v=0;
                        Iterator iteraor = ESP.entrySet().iterator();
                            while(iteraor.hasNext()){
                                Map.Entry entryd = (Map.Entry) iterator.next();
    v++;
    System.out.println("tab"+tabESP.get(v));
    System.out.println(ESP.get(entryd));
}*/
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    void actu() {

        if (valueEventListenerDate != null) {
            myRef.child("ESP32").child(oldChoixESP).child("TauxRafraichissement").removeEventListener(valueEventListenerTemps);
        }
        if (valueEventListenerTemps != null) {
            dataESP.deleteAllData();
            myRef.child("ESP32").child(oldChoixESP).child("Mesure").removeEventListener(valueEventListenerDate);
        }

        valueEventListenerDate = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Data a = dataSnapshot.getValue(Data.class);
                    dataESP.list_add_data(a);
                }
                dataAdapter.notifyDataSetChanged();
                recyclerView.invalidate();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        myRef.child("ESP32").child(choixESP).child("Mesure").addValueEventListener(valueEventListenerDate);
        valueEventListenerTemps = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String heure = "";
                String minute = "";
                String seconde = "";
                if (snapshot.exists()) {
                    if (snapshot.getValue(Long.class) >= 3600000) {
                        heure = (snapshot.getValue(Long.class) / (1000 * 60 * 60) + "h");
                    }
                    if (snapshot.getValue(Long.class) >= 60000) {
                        minute = (snapshot.getValue(Long.class) % (1000 * 60 * 60)) / (1000 * 60) + "m";
                    }
                    if (snapshot.getValue(Long.class) >= 1000) {
                        seconde = (snapshot.getValue(Long.class) % (1000 * 60)) / 1000 + "s";
                    }
                    refresh.setHint(heure + minute + seconde);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        myRef.child("ESP32").child(choixESP).child("TauxRafraichissement").addValueEventListener(valueEventListenerTemps);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rennoméA:
                Pop_up customPopup = new Pop_up(this);
                customPopup.build("Rennomé l'esp", "Nom", 1);
                customPopup.getYesButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!customPopup.getString().equals("")) {
                            myRef.child(choixESP).child("Nom").setValue(customPopup.getString());
                            //  adapter.notifyDataSetChanged();
                            customPopup.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(), "Merci d'entrer un nom", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                customPopup.getNoButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customPopup.dismiss();
                    }
                });
                //Faire dans pop up
                break;
            case R.id.suppA:
                Pop_up deletePopup = new Pop_up(this);
                deletePopup.build("Supprimer l'esp " + choixESP);
                deletePopup.getYesButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myRef.child(choixESP).removeValue();
                        deletePopup.dismiss();

                    }
                });
                deletePopup.getNoButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deletePopup.dismiss();
                    }
                });
                break;
            case R.id.grouperA:
                break;
            case R.id.valiRefresh:
                if (refresh.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Merci d'entrer' une valeur", Toast.LENGTH_SHORT).show();
                } else {
                    myRef.child("ESP32").child(choixESP).child("TauxRafraichissement").setValue((Double.valueOf(refresh.getText().toString()) * 1000));
                    Toast.makeText(getApplicationContext(), "Refresh : " + refresh.getText() + "s,\r\nVous pouvez redémarrer l'ESP", Toast.LENGTH_LONG).show();
                    refresh.setText("");
                }
                break;
            case R.id.reiniA:
                Pop_up popReini = new Pop_up(this);
                popReini.build("En êtes vous sûr?");
                popReini.getYesButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myRef.child("ESP32").child(choixESP).child("Mesure").removeValue();
                        myRef.child("ESP32").child(choixESP).child("MesureNumber").removeValue();
                        popReini.dismiss();
                        actu();
                        dataAdapter.notifyDataSetChanged();
                        recyclerView.invalidate();
                    }


                });
                //spinner.getAdapter().notify();
                popReini.getNoButton().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popReini.dismiss();
                    }
                });

                break;

        }

    }
}