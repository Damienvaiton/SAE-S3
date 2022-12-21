package com.example.pageacceuil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

public class VueData extends AppCompatActivity {

    ListData listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_data);


        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("listData")) {
                this.listData = (ListData) intent.getSerializableExtra("listData");
                System.out.println("yes bg");
            } else {
                System.out.println("erreur");
            }
        }

        listData.list_add_data(new Data(45,23,230,23,73,"15:23:30"));
        listData.list_add_data(new Data(45,23,230,23,73,"15:23:30"));
        listData.list_add_data(new Data(45,23,230,23,73,"15:23:30"));
        listData.list_add_data(new Data(45,23,230,23,73,"15:23:30"));
        listData.list_add_data(new Data(45,23,230,23,73,"15:23:30"));
        listData.list_add_data(new Data(45,23,230,23,73,"15:23:30"));
        listData.list_add_data(new Data(45,23,230,23,73,"15:23:30"));
        listData.list_add_data(new Data(45,23,230,23,73,"15:23:30"));
        listData.list_add_data(new Data(45,23,230,23,73,"15:23:30"));
        listData.list_add_data(new Data(45,23,230,23,73,"15:23:30"));
        listData.list_add_data(new Data(45,23,230,23,73,"15:23:30"));

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager((new LinearLayoutManager((this))));
        recyclerView.setAdapter(new DataAdapter(getApplicationContext(), listData));
    }
}
