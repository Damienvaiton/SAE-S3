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


    String ChoixESP = "";
    ImageButton btnselect;
    Button btnsound;
    ImageButton btncoEtu;
    ImageButton btnCoAdmin;
    Button btnGraph;
    String[] temp;
    HashMap<String,String> ESP;
    ArrayList<String> tabESP;

    static String ChoixEspTransfert = "1";

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
                Toast.makeText(MainActivity.this,"le "+position,Toast.LENGTH_SHORT).show();
                int curseur=0;
                for (Map.Entry entree : ESP.entrySet()) {
                    curseur++;
                    if (curseur==position){
                        ChoixESP=(String)entree.getKey();
                    }
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
//                        ESP.add((String)child.child("Nom").getValue());
                        ESP.put(child.getKey(), (String) child.child("Nom").getValue());
                    } else {
//                        ESP.add(child.getKey());
                        ESP.putIfAbsent(child.getKey(), null);
                    }
                }
                Iterator iterator = ESP.entrySet().iterator();
                tabESP.clear();
                while (iterator.hasNext()) {
                    Map.Entry mapentry = (Map.Entry) iterator.next();
//                    System.out.println(mapentry.getValue()+" "+mapentry.getKey());
                    if(mapentry.getValue()==null) {
                        tabESP.add((String) mapentry.getKey());
                    }
                    else {
                        tabESP.add((String) mapentry.getValue());
                    }
                }

                adapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        btnselect.setOnClickListener(new View.OnClickListener() {
        /*    @Override
            public void onClick(View view) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, btnselect);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_choise, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(MainActivity.this, "Vous avez choisi " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        ChoixESP = (String) item.getTitle();
                        String[] choixe;
                        choixe = ChoixESP.split(" ");
                        TextView textView = findViewById(R.id.IndiqueESPChoise);

                        textView.setText(choixe[1]);
                        ChoixEspTransfert = choixe[1];
                        temp = ChoixEspTransfert.split("°");
                        ChoixEspTransfert = temp[1];


                        return true;
                    }
                });

                popup.show();//showing popup menu
            }

        });
*/

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


