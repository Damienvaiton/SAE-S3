package com.example.saes3.View;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saes3.Model.Data;
import com.example.saes3.R;
import com.example.saes3.ViewModel.VueDataViewModel;
import com.example.saes3.ViewModel.dataRecyclerAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class VueDataActivity extends AppCompatActivity implements Serializable {

    private RecyclerView recyclerView;
    private Button btnTriChoix;
    private Button trid;
    private Switch switchDesc;
    private dataRecyclerAdapter dataRecyclerAdapter;
    private String ChoixTri = "Choix du Tri";
    private boolean Desc = false;

    /**
     * The ViewModel for VueDataActivity
     */
    private VueDataViewModel vueDataViewModel=null;

    private ArrayList<Data> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_data);

        vueDataViewModel=new ViewModelProvider(this).get(VueDataViewModel.class);


        vueDataViewModel.getData().observe(this, new Observer<Data>() {
            @Override
            public void onChanged(Data data) {
                dataRecyclerAdapter.notifyItemInserted(dataRecyclerAdapter.getItemCount()-1);
            }
        });

        //Faire une interface commune pour les mutablesLiveDta afin que ce firebassacess ne soit pas dépendant d'un viewmodel et les garder pour tout viewmodel ayant besoin


        btnTriChoix = findViewById(R.id.SortChoise);
        switchDesc = findViewById(R.id.switch1);
        trid = findViewById(R.id.button8);
        btnTriChoix.setText(ChoixTri);

        dataRecyclerAdapter = new dataRecyclerAdapter(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(dataRecyclerAdapter);
        recyclerView.setLayoutManager((new LinearLayoutManager((this))));


        vueDataViewModel.getData().observe(this, new Observer<Data>() {
            @Override
            public void onChanged(Data data) {
                dataRecyclerAdapter.notifyItemInserted(dataRecyclerAdapter.getItemCount()-1);
            }
        });

        btnTriChoix.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(VueDataActivity.this, btnTriChoix);
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


/**
 * Sort the value in adapter by the 'ChoixTri' value and the 'Desc' boolean
 */
        trid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Jpeux faire une enum
                switch (ChoixTri.toString()) {
                    case "Co²":
                        if (Desc) {
                            Collections.sort(listData, (e1, e2) -> Float.valueOf(e1.getCO2()).compareTo(Float.valueOf(e2.getCO2())));
                            dataRecyclerAdapter.notifyDataSetChanged();
                        } else {
                            Collections.sort(listData, (e1, e2) -> Float.valueOf(e1.getCO2()).compareTo(Float.valueOf(e2.getCO2())));
                            dataRecyclerAdapter.notifyDataSetChanged();
                        }
                    case "Température":
                        if (Desc) {
                            Collections.sort(listData, (e1, e2) -> Float.valueOf(e1.getTemperature()).compareTo(Float.valueOf(e2.getTemperature())));
                            dataRecyclerAdapter.notifyDataSetChanged();
                        } else {
                            Collections.sort(listData, (e1, e2) -> Float.valueOf(e1.getTemperature()).compareTo(Float.valueOf(e2.getTemperature())));
                            dataRecyclerAdapter.notifyDataSetChanged();
                        }
                    case "Humidité":
                        if (Desc) {
                            Collections.sort(listData, (e1, e2) -> Float.valueOf(e1.getHumidite()).compareTo(Float.valueOf(e2.getHumidite())));
                            dataRecyclerAdapter.notifyDataSetChanged();
                        } else {
                            Collections.sort(listData, (e1, e2) -> Float.valueOf(e1.getHumidite()).compareTo(Float.valueOf(e2.getHumidite())));
                            dataRecyclerAdapter.notifyDataSetChanged();
                        }
                    case "Luminiosité":
                        if (Desc) {
                            Collections.sort(listData, (e1, e2) -> Float.valueOf(e1.getLight()).compareTo(Float.valueOf(e2.getLight())));
                            dataRecyclerAdapter.notifyDataSetChanged();
                        } else {
                            Collections.sort(listData, (e1, e2) -> Float.valueOf(e1.getLight()).compareTo(Float.valueOf(e2.getLight())));
                            dataRecyclerAdapter.notifyDataSetChanged();
                        }
                    case "O²":
                        if (Desc) {
                            Collections.sort(listData, (e1, e2) -> Float.valueOf(e1.getLight()).compareTo(Float.valueOf(e2.getLight())));
                            dataRecyclerAdapter.notifyDataSetChanged();
                        } else {
                            Collections.sort(listData, (e1, e2) -> Float.valueOf(e1.getLight()).compareTo(Float.valueOf(e2.getLight())));
                            dataRecyclerAdapter.notifyDataSetChanged();
                        }
                    default:
                        break;

                }
            }
            });
        }
    }

