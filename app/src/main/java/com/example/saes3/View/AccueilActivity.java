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
import androidx.lifecycle.ViewModelProvider;

import com.example.saes3.R;
import com.example.saes3.ViewModel.AccueilViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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
     * Button for switch in the GuideInstaView
     */
    private FloatingActionButton helper;

    /**
     * Store the index of ESP actually selected
     */
    private String nomESP;
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
     * Erase his instance when we left the page
     */
    @Override
    protected void onPause() {
        super.onPause();
        accueilViewModel.pause();
    }

    /**
     * Set his instance to the transiClass in restart for obtain data
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        accueilViewModel.setInstance();
    }

    /**
     * OnCreate initialize objet in the view
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accueilViewModel = new ViewModelProvider(this).get(AccueilViewModel.class);
        spinner = findViewById(R.id.SpinnerID);
        btncoEtu = findViewById(R.id.Gobtn);
        helper=findViewById(R.id.helper);
        btnCoAdmin = findViewById(R.id.adminbtnmain);
        tabESP = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AccueilActivity.this, android.R.layout.simple_spinner_dropdown_item, tabESP);
        spinner.setAdapter(adapter);


        /**
         * Observe a LiveData and recreate tabESP to keep up to date with ESP available in the database
         */
        accueilViewModel.getHashESP().observe(this, strings -> {
            tabESP.clear();
            for (String ESP : strings) {
                tabESP.add(ESP);
            }
            adapter.notifyDataSetChanged();
        });
        /**
         * Store the value selected into 'position'
         */
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                nomESP = parent.getItemAtPosition(pos).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("Spinner", "Nothing selected");
            }
        });

        /**
         * Launch ConnecAdminActivity if "btnCoAdmin" triggered
         */
        btncoEtu.setOnClickListener(view -> {
            if(!adapter.isEmpty() ) {
                Intent graph;
                accueilViewModel.creaESP(nomESP);
                graph = new Intent(AccueilActivity.this, GraphiqueActivity.class);
                startActivity(graph);
            }else{
                Snackbar.make(btncoEtu,"Merci de patienter jusqu'à la fin du chargement",Snackbar.LENGTH_LONG).show();
            }
        });

        /**
         * Launch ConnecAdminActivity if "btnCoAdmin" triggered
         */
        btnCoAdmin.setOnClickListener(view -> {
            Intent admin;
            admin = new Intent(getApplicationContext(), ConnecAdminActivity.class);
            startActivity(admin);

        });
        /**
         * Launch GuideInstaView if triggered
         */
        helper.setOnClickListener(v -> startActivity(new Intent(AccueilActivity.this, GuideInstaView.class)));

    }

}