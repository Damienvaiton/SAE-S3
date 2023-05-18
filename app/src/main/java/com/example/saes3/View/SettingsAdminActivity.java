
package com.example.saes3.View;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.saes3.Model.ListData;
import com.example.saes3.R;
import com.example.saes3.Util.PopUpDialog;
import com.example.saes3.ViewModel.SettingsAdminViewModel;
import com.example.saes3.Util.dataRecyclerAdapter;

import java.util.ArrayList;

public class SettingsAdminActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private Spinner spinner;
    private Button valiRefresh;
    private Button rename;
    private Button delete;
    private EditText refresh;
    private Button grouper;
    private Button reini;

    private ArrayAdapter<String> adapterRecyclerESP;
    private ArrayList<String> tabESP;


    private dataRecyclerAdapter dataRecyclerAdapter;


    private SettingsAdminViewModel settingsAdminViewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_setting_admin);

        settingsAdminViewModel = new ViewModelProvider(this).get(SettingsAdminViewModel.class);

        rename = findViewById(R.id.rennoméA);
        delete = findViewById(R.id.suppA);
        refresh = findViewById(R.id.refreshA);
        grouper = findViewById(R.id.grouperA);
        reini = findViewById(R.id.reiniA);
        spinner = findViewById(R.id.spinnerAdmin);
        valiRefresh = findViewById(R.id.valiRefresh);
        recyclerView = findViewById(R.id.recyclerViewAdmin);


        valiRefresh.setOnClickListener(this);
        rename.setOnClickListener(this);
        delete.setOnClickListener(this);
        grouper.setOnClickListener(this);
        reini.setOnClickListener(this);
        tabESP = new ArrayList<>();

        adapterRecyclerESP = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, tabESP);
        spinner.setAdapter(adapterRecyclerESP);
        dataRecyclerAdapter = new dataRecyclerAdapter(getApplicationContext(), ListData.getInstance().getListAllData());
        recyclerView.setAdapter(dataRecyclerAdapter);
        recyclerView.setLayoutManager((new LinearLayoutManager((this))));

        AlertDialog.Builder pop = new AlertDialog.Builder(SettingsAdminActivity.this);
        pop.setMessage("Assurez-vous qu'avant toute modification l'ESP est éteint.");
        pop.setPositiveButton("Compris", (dialog, which) -> {
            Toast.makeText(getApplicationContext(), "Prêt", Toast.LENGTH_SHORT).show();
            dialog.cancel();
        });
        pop.show();

        settingsAdminViewModel.getListenerData().observe(this, data -> dataRecyclerAdapter.notifyDataSetChanged());

        settingsAdminViewModel.getHashmapESP().observe(this, strings -> {
            tabESP.clear();
            for (String ESP : strings) {
                if (!tabESP.contains(ESP)) {
                    tabESP.add(ESP);
                }
            }
            adapterRecyclerESP.notifyDataSetChanged();
        });


        settingsAdminViewModel.getTempsAdmin().observe(this, s -> refresh.setHint(s));


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                settingsAdminViewModel.clearTab();
                dataRecyclerAdapter.notifyDataSetChanged();
                settingsAdminViewModel.creaESP(parent.getItemAtPosition(position).toString());
                settingsAdminViewModel.setListenerESP(); // Mettre tout les listener dedans
            }

            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("Spinner", "Nothing selected");

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rennoméA:
                PopUpDialog customPopup = new PopUpDialog(this);
                customPopup.build("Renommé l'esp", "Nom", 1);
                customPopup.getYesButton().setOnClickListener(view -> {
                    if (!customPopup.getString().equals("")) {
                        settingsAdminViewModel.renameESP(customPopup.getString());
                        customPopup.dismiss();
                    } else {
                        Toast.makeText(getApplicationContext(), "Merci d'entrer un nom", Toast.LENGTH_SHORT).show();

                    }
                });
                customPopup.getNoButton().setOnClickListener(view -> customPopup.dismiss());
                //Faire dans pop up
                break;
            case R.id.suppA:
                PopUpDialog deletePopup = new PopUpDialog(this);
                deletePopup.build("Supprimer l'esp ?");
                deletePopup.getYesButton().setOnClickListener(view -> {
                    settingsAdminViewModel.suppESP();
                    deletePopup.dismiss();

                });
                deletePopup.getNoButton().setOnClickListener(view -> deletePopup.dismiss());
                break;
            case R.id.grouperA:
                break;
            case R.id.valiRefresh:
                if (refresh.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Merci d'entrer' une valeur", Toast.LENGTH_SHORT).show();
                } else {
                    settingsAdminViewModel.setESPrefresh(refresh.getText().toString());
                    refresh.setText("");
                }
                break;
            case R.id.reiniA:
                PopUpDialog popReini = new PopUpDialog(this);
                popReini.build("En êtes vous sûr?");
                popReini.getYesButton().setOnClickListener(view -> {
                    settingsAdminViewModel.resetESP();
                    popReini.dismiss();
                    dataRecyclerAdapter.notifyItemRangeRemoved(0, dataRecyclerAdapter.getItemCount());
                    recyclerView.invalidate();
                });
                popReini.getNoButton().setOnClickListener(view -> popReini.dismiss());

                break;

            default:
                break;


        }

    }

}

