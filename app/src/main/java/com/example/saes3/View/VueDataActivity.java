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
import com.example.saes3.Model.ListData;
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
    private ArrayList<Data> cloneListData;

    /**
     * The ViewModel for VueDataActivity
     */
    private VueDataViewModel vueDataViewModel = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vue_data);

        vueDataViewModel = new ViewModelProvider(this).get(VueDataViewModel.class);

        cloneListData= (ArrayList<Data>) ListData.getInstance().getListAllData().clone();

        btnTriChoix = findViewById(R.id.SortChoise);
        switchDesc = findViewById(R.id.switch1);
        trid = findViewById(R.id.button8);
        btnTriChoix.setText(ChoixTri);

        dataRecyclerAdapter = new dataRecyclerAdapter(getApplicationContext(),cloneListData);
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
            switch (ChoixTri) {
                case "Co²":
                    if (Desc) {
                        cloneListData.sort((e1, e2) -> Float.compare(e1.getCo2(), e2.getCo2()));
                        dataRecyclerAdapter.notifyDataSetChanged();

                    } else {
                        cloneListData.sort((e1, e2) -> Float.compare(e2.getCo2(), e1.getCo2()));
                        dataRecyclerAdapter.notifyDataSetChanged();

                    }
                    break;
                case "Température":
                    if (Desc) {
                        cloneListData.sort((e1, e2) -> Float.compare(e1.getTemperature(), e2.getTemperature()));
                        dataRecyclerAdapter.notifyDataSetChanged();
                        break;
                    } else {
                        cloneListData.sort((e1, e2) -> Float.compare(e2.getTemperature(), e1.getTemperature()));
                        dataRecyclerAdapter.notifyDataSetChanged();
                    }
                    break;
                case "Humidité":
                    if (Desc) {
                        cloneListData.sort((e1, e2) -> Float.compare(e1.getHumidite(), e2.getHumidite()));
                        dataRecyclerAdapter.notifyDataSetChanged();
                    } else {
                        cloneListData.sort((e1, e2) -> Float.compare(e2.getHumidite(), e1.getHumidite()));
                        dataRecyclerAdapter.notifyDataSetChanged();
                    }
                    break;
                case "Luminiosité":
                    if (Desc) {
                        cloneListData.sort((e1, e2) -> Float.compare(e1.getLight(), e2.getLight()));
                        dataRecyclerAdapter.notifyDataSetChanged();
                    } else {
                        cloneListData.sort((e1, e2) -> Float.compare(e2.getLight(), e1.getLight()));
                        dataRecyclerAdapter.notifyDataSetChanged();
                    }
                    break;
                case "O²":
                    if (Desc) {
                        cloneListData.sort((e1, e2) -> Float.compare(e1.getO2(), e2.getO2()));
                        dataRecyclerAdapter.notifyDataSetChanged();
                    } else {
                        cloneListData.sort((e1, e2) -> Float.compare(e2.getO2(), e1.getO2()));
                        dataRecyclerAdapter.notifyDataSetChanged();
                    }
                    break;
                default:
                    break;

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}

