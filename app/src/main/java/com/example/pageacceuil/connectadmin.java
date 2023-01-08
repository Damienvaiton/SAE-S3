package com.example.pageacceuil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class connectadmin extends AppCompatActivity {

    ImageButton butnback;
    ImageButton butnnext;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connectadmin);

        butnback = findViewById(R.id.imageButton3);
        butnnext = findViewById(R.id.Boutonconnexionadmin);

        butnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back;
                back = new Intent(connectadmin.this, MainActivity.class);

                startActivity(back);
            }
        });
        butnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent next;
                next = new Intent(connectadmin.this, AdminPage.class);

                startActivity(next);
            }
        });
    }
}