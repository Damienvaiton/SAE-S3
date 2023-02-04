package com.example.pageacceuil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;

public class VueData extends AppCompatActivity implements Serializable {

    private ListData listData;
    private Button btnTriChoix;
    private Button trid;
    private Switch switchDesc;
    private String ChoixTri = "Choix du Tri";
    private boolean Desc = false;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_data);
        btnTriChoix = findViewById(R.id.SortChoise);
        switchDesc = findViewById(R.id.switch1);
        trid = findViewById(R.id.button8);
        btnTriChoix.setText(ChoixTri);
        DataAdapter dataAdapter = new DataAdapter(getApplicationContext());
        dataAdapter.notifyDataSetChanged();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(dataAdapter);
        listData=ListData.getInstance();

        btnTriChoix.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(VueData.this, btnTriChoix);
                popupMenu.getMenuInflater().inflate(R.menu.menu_tri_choix, popupMenu.getMenu());


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {


                        Desc = switchDesc.isChecked();
                        ChoixTri = (String) item.getTitle();

                        btnTriChoix.setText(ChoixTri);
                        return true;
                    }


                });
                popupMenu.show();
            }
        });


        recyclerView.setLayoutManager((new LinearLayoutManager((this))));


        trid.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {

                if (ChoixTri.equals("Co²")) {
                    if (Desc) {
                        listData.listsortCO2Desc();
                        dataAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataAdapter);
                    } else {
                        listData.listsortCO2();
                        dataAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataAdapter);
                    }
                }
                if (ChoixTri.equals("Température")) {
                    if (Desc) {
                        listData.listsortTempDesc();
                        dataAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataAdapter);
                    } else {
                        listData.listsortTemp();
                        dataAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataAdapter);
                    }
                }
                if (ChoixTri.equals("Humidité")) {
                    if (Desc) {
                        listData.listsortHumDesc();
                        dataAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataAdapter);
                    } else {
                        listData.listsortHum();
                        dataAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataAdapter);
                    }
                }
                if (ChoixTri.equals("Luminosité")) {
                    if (Desc) {
                        listData.listsortLuxDesc();
                        dataAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataAdapter);
                    } else {
                        listData.listsortLux();
                        dataAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataAdapter);
                    }
                }
                if (ChoixTri.equals("O²")) {
                    if (Desc) {
                        listData.listsortO2Desc();
                        dataAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataAdapter);
                    } else {
                        listData.listsortO2();
                        dataAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataAdapter);
                    }
                }


            }

        });


    }
}
