package com.example.pageacceuil;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {


    String ChoixESP = "";
    ImageButton btnselect;
    ImageButton btncoEtu;
    ImageButton btnCoAdmin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnselect = findViewById(R.id.Boutonsel);
        btncoEtu = findViewById(R.id.imageButton5);
        btnCoAdmin = findViewById(R.id.imageButton4);

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
                Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                ChoixESP = (String) item.getTitle();
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
    }



}


