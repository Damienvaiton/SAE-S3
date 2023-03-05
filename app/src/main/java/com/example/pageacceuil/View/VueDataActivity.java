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
import com.example.pageacceuil.Model.ListData;
import com.example.pageacceuil.R;
import com.example.pageacceuil.ViewModel.dataRecyclerAdapter;
import com.example.pageacceuil.ViewModel.VueDataViewModel;

import java.io.Serializable;

public class VueDataActivity extends AppCompatActivity implements Serializable {

    private ListData listData;

    private RecyclerView recyclerView;
    private Button btnTriChoix;
    private Button trid;
    private Switch switchDesc;

    dataRecyclerAdapter dataRecyclerAdapter;
    private String ChoixTri = "Choix du Tri";
    private boolean Desc = false;
    VueDataViewModel vueDataViewModel=null;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_data);

        vueDataViewModel=new ViewModelProvider(this).get(VueDataViewModel.class);

        //Faire une interface commune pour les mutablesLiveDta afin que ce firebassacess ne soit pas dépendant d'un viewmodel et les garder pour tout viewmodel ayant besoin


        btnTriChoix = findViewById(R.id.SortChoise);
        switchDesc = findViewById(R.id.switch1);
        trid = findViewById(R.id.button8);
        btnTriChoix.setText(ChoixTri);


        dataRecyclerAdapter=vueDataViewModel.getDataRecyclerAdapter();
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(dataRecyclerAdapter);
        recyclerView.setLayoutManager((new LinearLayoutManager((this))));




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





      /*  trid.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {

                if (ChoixTri.equals("Co²")) {
                    if (Desc) {
                        listData.listsortCO2Desc();
                        dataRecyclerAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataRecyclerAdapter);
                    } else {
                        listData.listsortCO2();
                        dataRecyclerAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataRecyclerAdapter);
                    }
                }
                if (ChoixTri.equals("Température")) {
                    if (Desc) {
                        listData.listsortTempDesc();
                        dataRecyclerAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataRecyclerAdapter);
                    } else {
                        listData.listsortTemp();
                        dataRecyclerAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataRecyclerAdapter);
                    }
                }
                if (ChoixTri.equals("Humidité")) {
                    if (Desc) {
                        listData.listsortHumDesc();
                        dataRecyclerAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataRecyclerAdapter);
                    } else {
                        listData.listsortHum();
                        dataRecyclerAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataRecyclerAdapter);
                    }
                }
                if (ChoixTri.equals("Luminosité")) {
                    if (Desc) {
                        listData.listsortLuxDesc();
                        dataRecyclerAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataRecyclerAdapter);
                    } else {
                        listData.listsortLux();
                        dataRecyclerAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataRecyclerAdapter);
                    }
                }
                if (ChoixTri.equals("O²")) {
                    if (Desc) {
                        listData.listsortO2Desc();
                        dataRecyclerAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataRecyclerAdapter);
                    } else {
                        listData.listsortO2();
                        dataRecyclerAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataRecyclerAdapter);
                    }
                }


            }

        });*/

        vueDataViewModel.getData().observe(this, new Observer<Data>() {
            @Override
            public void onChanged(Data data) {
                dataRecyclerAdapter.notifyItemInserted(dataRecyclerAdapter.getItemCount()-1);
            }
        });



    }

}
