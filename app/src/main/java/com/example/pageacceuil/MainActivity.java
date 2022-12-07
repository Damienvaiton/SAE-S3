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

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {



    String ChoixESP = "";
    ImageButton btnselect;
    ImageButton btncoEtu;
    ImageButton btnCoAdmin;
    Button btnGraph;
    String temp[];
    static String ChoixEspTransfert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btnselect = findViewById(R.id.Boutonsel);
        btncoEtu = findViewById(R.id.imageButton5);
        btnCoAdmin = findViewById(R.id.imageButton4);
        btnGraph=findViewById(R.id.btnGraph);

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
                        TextView textView = (TextView) findViewById(R.id.IndiqueESPChoise);

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

                startActivity(vu);
            }
        });
    }

}


