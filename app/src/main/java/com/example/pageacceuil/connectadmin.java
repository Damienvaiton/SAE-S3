package com.example.pageacceuil;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class connectadmin extends AppCompatActivity {

    ImageButton butnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connectadmin);

        butnback = findViewById(R.id.imageButton3);

        butnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back;
                back = new Intent(connectadmin.this, MainActivity.class);

                startActivity(back);
            }
        });
    }
}