package com.example.pageacceuil.View;

import android.annotation.SuppressLint;
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

import com.example.pageacceuil.Model.Data;
import com.example.pageacceuil.R;
import com.example.pageacceuil.ViewModel.VueDataViewModel;
import com.example.pageacceuil.ViewModel.dataRecyclerAdapter;

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
                            Collections.sort(listData, (e1, e2) -> Float.compare(e1.getCO2(), e2.getCO2()));
                            dataRecyclerAdapter.notifyDataSetChanged();
                        } else {
                            Collections.sort(listData, (e1, e2) -> Float.compare(e1.getCO2(), e2.getCO2()));
                            dataRecyclerAdapter.notifyDataSetChanged();
                        }
                    case "Température":
                        if (Desc) {
                            Collections.sort(listData, (e1, e2) -> Float.compare(e1.getTemperature(), e2.getTemperature()));
                            dataRecyclerAdapter.notifyDataSetChanged();
                        } else {
                            Collections.sort(listData, (e1, e2) -> Float.compare(e1.getTemperature(), e2.getTemperature()));
                            dataRecyclerAdapter.notifyDataSetChanged();
                        }
                    case "Humidité":
                        if (Desc) {
                            Collections.sort(listData, (e1, e2) -> Float.compare(e1.getHumidite(), e2.getHumidite()));
                            dataRecyclerAdapter.notifyDataSetChanged();
                        } else {
                            Collections.sort(listData, (e1, e2) -> Float.compare(e1.getHumidite(), e2.getHumidite()));
                            dataRecyclerAdapter.notifyDataSetChanged();
                        }
                    case "Luminiosité":
                        if (Desc) {
                            Collections.sort(listData, (e1, e2) -> Float.compare(e1.getLight(), e2.getLight()));
                            dataRecyclerAdapter.notifyDataSetChanged();
                        } else {
                            Collections.sort(listData, (e1, e2) -> Float.compare(e1.getLight(), e2.getLight()));
                            dataRecyclerAdapter.notifyDataSetChanged();
                        }
                    case "O²":
                        if (Desc) {
                            Collections.sort(listData, (e1, e2) -> Float.compare(e1.getLight(), e2.getLight()));
                            dataRecyclerAdapter.notifyDataSetChanged();
                        } else {
                            Collections.sort(listData, (e1, e2) -> Float.compare(e1.getLight(), e2.getLight()));
                            dataRecyclerAdapter.notifyDataSetChanged();
                        }
                    default:
                        break;

                }
            }
            });
        }
    }

