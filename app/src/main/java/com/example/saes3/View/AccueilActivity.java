package com.example.saes3.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.saes3.R;
import com.example.saes3.ViewModel.AccueilViewModel;

import java.util.ArrayList;


public class AccueilActivity extends AppCompatActivity {

    /**
     * Instance of the ViewModel
     */
    private AccueilViewModel accueilViewModel = null;

    /**
     * Spinner who shows all ESP available
     */
    private Spinner spinner;
    /**
     * Store the index of ESP actually selected
     */
    private int position;
    /**
     * Button for switch to 'GraphView'
     */
    private Button btncoEtu;
    /**
     * Button for switch to 'ConnectAdminView'
     */
    private Button btnCoAdmin;
    /**
     * Arraylist who contains all ESP available
     */
    private ArrayList<String> tabESP;

    /**
     *
     */
    @Override
    protected void onPause() {
        super.onPause();
        accueilViewModel.pause();
    }

    /**
     *
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        accueilViewModel.setInstance();
    }

    /**
     *
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accueilViewModel = new ViewModelProvider(this).get(AccueilViewModel.class);
        spinner = findViewById(R.id.SpinnerID);
        btncoEtu = findViewById(R.id.Gobtn);
        btnCoAdmin = findViewById(R.id.adminbtnmain);
        tabESP = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AccueilActivity.this, android.R.layout.simple_spinner_dropdown_item, tabESP);
        spinner.setAdapter(adapter);

        /**
         * Observe a LiveData and recreate tabESP to keep up to date with ESP available in the database
         */
        accueilViewModel.getESP().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                tabESP.clear();
                for (String ESP : strings) {
                    tabESP.add(ESP.toString());
                }
                adapter.notifyDataSetChanged();
            }
        });
        /**
         * Store the value selected into 'position'
         */
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                position = pos;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("Spinner", "Nothing selected");
            }
        });

        /**
         * Launch ConnecAdminActivity if "btnCoAdmin" triggered
         */
        btncoEtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent graph;
                accueilViewModel.creaESP(position);
                graph = new Intent(AccueilActivity.this, GraphiqueActivity.class);
                startActivity(graph);
            }
        });

        /**
         * Launch ConnecAdminActivity if "btnCoAdmin" triggered
         */
        btnCoAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent admin;
                admin = new Intent(getApplicationContext(), ConnecAdminActivity.class);
                startActivity(admin);

            }
        });


    }

}