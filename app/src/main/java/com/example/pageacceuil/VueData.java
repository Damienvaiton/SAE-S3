package com.example.pageacceuil;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class VueData extends AppCompatActivity implements Serializable {

    private ListData listData;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_data);
/*

        Intent intent = getIntent();

        if (intent != null) {
            if (intent.hasExtra("listData")) {

                this.listData = (ListData) intent.getSerializableExtra("listData");
                System.out.println("ok");
                new VueData();
            } else {
                System.out.println("erreur");
            }
        }
*/





        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager((new LinearLayoutManager((this))));
        recyclerView.setAdapter(new DataAdapter(getApplicationContext(), GraphPage.listData));
    }
}
