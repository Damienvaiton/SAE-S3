package com.example.pageacceuil;

import android.annotation.SuppressLint;
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
/*



        Intent intent = getIntent();

        if (intent != null) {
            if (intent.hasExtra("listData")) {

                this.listData = (ListData) intent.getSerializableExtra("listData");
                System.out.println("ok");
            } else {
                System.out.println("erreur");
            }
        }
*/


        btnTriChoix.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(VueData.this, btnTriChoix);
                popupMenu.getMenuInflater().inflate(R.menu.menu_tri_choix, popupMenu.getMenu());


                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {


                        if (switchDesc.isChecked()) {
                            Desc = true;
                        } else {
                            Desc = false;
                        }
                        ChoixTri = (String) item.getTitle();

                        btnTriChoix.setText(ChoixTri);
                        return true;
                    }


                });
                popupMenu.show();
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager((new LinearLayoutManager((this))));


        trid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataAdapter dataAdapter = new DataAdapter(getApplicationContext(), GraphPage.listData);
                if (ChoixTri.equals("Co²")) {
                    if (Desc) {
                        GraphPage.listData.listsortCO2Desc();
                        dataAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataAdapter);
                    } else {
                        GraphPage.listData.listsortCO2();
                        dataAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataAdapter);                    }
                }
                if (ChoixTri.equals("Température")) {
                    if (Desc) {
                        GraphPage.listData.listsortTempDesc();
                        dataAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataAdapter);
                    } else {
                        GraphPage.listData.listsortTemp();
                        dataAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataAdapter);
                    }
                }
                if (ChoixTri.equals("Humidité")) {
                    if (Desc) {
                        GraphPage.listData.listsortHumDesc();
                        dataAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataAdapter);
                    } else {
                        GraphPage.listData.listsortHum();
                        dataAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataAdapter);
                    }
                }
                if (ChoixTri.equals("Luminosité")) {
                    if (Desc) {
                        GraphPage.listData.listsortLuxDesc();
                        dataAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataAdapter);
                    } else {
                        GraphPage.listData.listsortLux();
                        dataAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataAdapter);
                    }
                }
                if (ChoixTri.equals("O²")) {
                    if (Desc) {
                        GraphPage.listData.listsortO2Desc();
                        dataAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataAdapter);
                    } else {
                        GraphPage.listData.listsortO2();
                        dataAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(dataAdapter);
                    }
                }


            }

        });

    }
}
