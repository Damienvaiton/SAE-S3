package com.example.pageacceuil.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.pageacceuil.R;
import com.example.pageacceuil.ViewModel.ConnectAdminViewModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ConnecAdminActivity extends AppCompatActivity {

    private EditText editUser;
    private EditText editMdp;
    private Button coBtn;


    private ConnectAdminViewModel connectAdminViewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connectadmin);

        // on demande au système de créer l'instance de Mainviewmodel si elle existe pas et de
        // lier son cycle de vie à l'activity courrante
        connectAdminViewModel = new ViewModelProvider(this).get(ConnectAdminViewModel.class);

        editMdp = findViewById(R.id.coMdp);
        editUser = findViewById(R.id.coUsername);
        coBtn = findViewById(R.id.coBtn);
        coBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectAdminViewModel.Verify(editUser.getText().toString(), editMdp.getText().toString())) {
                    Intent admin;
                   // admin = new Intent(ConnecAdminActivity.this, SettingsAdminActivity.class);
                    //startActivity(admin);
                }
                Toast.makeText(ConnecAdminActivity.this, "Une erreur se trouve dans le formulaire", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
