package com.example.saes3.View;

import android.os.Bundle;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saes3.Model.Data;
import com.example.saes3.R;
import com.example.saes3.ViewModel.VueDataViewModel;
import com.example.saes3.Util.dataRecyclerAdapter;

import java.io.Serializable;
import java.util.ArrayList;

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
    private VueDataViewModel vueDataViewModel = null;

    private ArrayList<Data> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_data);

        vueDataViewModel = new ViewModelProvider(this).get(VueDataViewModel.class);


        btnTriChoix = findViewById(R.id.SortChoise);
        switchDesc = findViewById(R.id.switch1);
        trid = findViewById(R.id.button8);
        btnTriChoix.setText(ChoixTri);

        dataRecyclerAdapter = new dataRecyclerAdapter(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(dataRecyclerAdapter);
        recyclerView.setLayoutManager((new LinearLayoutManager((this))));


        vueDataViewModel.getData().observe(this, data -> dataRecyclerAdapter.notifyItemInserted(dataRecyclerAdapter.getItemCount() - 1));

        btnTriChoix.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(VueDataActivity.this, btnTriChoix);
            popupMenu.getMenuInflater().inflate(R.menu.menu_tri_choix, popupMenu.getMenu());


            popupMenu.setOnMenuItemClickListener(item -> {
                Desc = switchDesc.isChecked();
                ChoixTri = (String) item.getTitle();
                btnTriChoix.setText(ChoixTri);
                return true;
            });
            popupMenu.show();
        });


/**
 * Sort the value in adapter by the 'ChoixTri' value and the 'Desc' boolean
 */
        trid.setOnClickListener(v -> {
            //Jpeux faire une enum
            switch (ChoixTri) {
                case "Co²":
                    if (Desc) {
                        listData.sort((e1, e2) -> Float.compare(e1.getCO2(), e2.getCO2()));
                        dataRecyclerAdapter.notifyDataSetChanged();

                    } else {
                        listData.sort((e1, e2) -> Float.compare(e1.getCO2(), e2.getCO2()));
                        dataRecyclerAdapter.notifyDataSetChanged();

                    }
                    break;
                case "Température":
                    if (Desc) {
                        listData.sort((e1, e2) -> Float.compare(e1.getTemperature(), e2.getTemperature()));
                        dataRecyclerAdapter.notifyDataSetChanged();
                        break;
                    } else {
                        listData.sort((e1, e2) -> Float.compare(e1.getTemperature(), e2.getTemperature()));
                        dataRecyclerAdapter.notifyDataSetChanged();
                    }
                    break;
                case "Humidité":
                    if (Desc) {
                        listData.sort((e1, e2) -> Float.compare(e1.getHumidite(), e2.getHumidite()));
                        dataRecyclerAdapter.notifyDataSetChanged();
                    } else {
                        listData.sort((e1, e2) -> Float.compare(e1.getHumidite(), e2.getHumidite()));
                        dataRecyclerAdapter.notifyDataSetChanged();
                    }
                    break;
                case "Luminiosité":
                    if (Desc) {
                        listData.sort((e1, e2) -> Float.compare(e1.getLight(), e2.getLight()));
                        dataRecyclerAdapter.notifyDataSetChanged();
                    } else {
                        listData.sort((e1, e2) -> Float.compare(e1.getLight(), e2.getLight()));
                        dataRecyclerAdapter.notifyDataSetChanged();
                    }
                    break;
                case "O²":
                    if (Desc) {
                        listData.sort((e1, e2) -> Float.compare(e1.getO2(), e2.getO2()));
                        dataRecyclerAdapter.notifyDataSetChanged();
                    } else {
                        listData.sort((e1, e2) -> Float.compare(e1.getO2(), e2.getO2()));
                        dataRecyclerAdapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;

            }
        });
    }
}

