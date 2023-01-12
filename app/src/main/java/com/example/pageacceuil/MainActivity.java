package com.example.pageacceuil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
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


public class MainActivity extends AppCompatActivity {


    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("SAE_S3_BD/ESP32");


    String ChoixESP = "";
    Button btnselect;
    Button btnsound;
    Button btncoEtu;
    Button btnCoAdmin;
    Button btnGraph;
    String[] temp;
    ArrayList<String> ESP;

    static String ChoixEspTransfert = "1";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnselect = findViewById(R.id.Spinnerid);
        btnCoAdmin = findViewById(R.id.adminbtnmain);
        btnGraph = findViewById(R.id.Gobtn);
ESP=new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot child : snapshot.getChildren()) {
                    if (child.child("Nom").exists()) {
                        ESP.add((String)child.child("Nom").getValue());
                    } else {
                        ESP.add(child.getKey());
                    }

                    }
                for (int i=0;i<2;i++){
                    System.out.println(ESP.get(i));
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
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


                        ChoixEspTransfert = choixe[1];
                        temp = ChoixEspTransfert.split("°");
                        ChoixEspTransfert = temp[1];


                        return true;
                    }
                });

                popup.show();//showing popup menu
            }

        });


        btncoEtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent connec;
                connec = new Intent(MainActivity.this, GraphPage.class);

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

    }

}


