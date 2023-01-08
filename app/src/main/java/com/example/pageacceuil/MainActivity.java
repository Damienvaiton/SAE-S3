package com.example.pageacceuil;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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


    String ChoixESP;
    ImageButton btnselect;
    ImageButton btncoEtu;
    ImageButton btnCoAdmin;
    Button btnGraph;
    HashMap<String,String> ESP;
    ArrayList<String> tabESP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnselect = findViewById(R.id.Boutonsel);
        btncoEtu = findViewById(R.id.imageButton5);
        btnCoAdmin = findViewById(R.id.imageButton4);
        btnGraph = findViewById(R.id.btnGraph);
        ESP=new HashMap<>();
        tabESP=new ArrayList<>();


        Spinner spinner=findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item,tabESP);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int curseur=0;
                for (Map.Entry entree : ESP.entrySet()) {

                    if (curseur==position){
                        ChoixESP=(String)entree.getKey();
                        System.out.println((String)entree.getKey());
                    } curseur++;
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
                        ESP.put(child.getKey(), (String) child.child("Nom").getValue());
                    } else {
                        ESP.putIfAbsent(child.getKey(), null);
                    }
                }
                Iterator iterator = ESP.entrySet().iterator();
                tabESP.clear();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    if(entry.getValue()==null) {
                        tabESP.add((String) entry.getKey());
                    }
                    else {
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
                Intent connec;
                connec = new Intent(MainActivity.this, connectetu.class);

                startActivity(connec);
            }
        });

        btnCoAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent connect;
                connect = new Intent(MainActivity.this, connectadmin.class);
                startActivity(connect);
            }
        });

        btnGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vu;
                vu = new Intent(MainActivity.this, GraphPage.class);
                vu.putExtra("ESP", ChoixESP);
                startActivity(vu);
            }
        });
    }

}


