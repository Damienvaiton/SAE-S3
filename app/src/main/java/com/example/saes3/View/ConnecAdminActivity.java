package com.example.saes3.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.saes3.R;
import com.example.saes3.ViewModel.ConnectAdminViewModel;

public class ConnecAdminActivity extends AppCompatActivity {

    /**
     * EditText for the id of admin
     */
    private EditText editUser;
    /**
     * EditText for the password of admin
     */
    private EditText editMdp;
    /**
     * Button to valid the login
     */
    private Button coBtn;

    /**
     * ViewModel of this view
     */
    private ConnectAdminViewModel connectAdminViewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connectadmin);

        connectAdminViewModel = new ViewModelProvider(this).get(ConnectAdminViewModel.class);

        editMdp = findViewById(R.id.coMdp);
        editUser = findViewById(R.id.coUsername);
        coBtn = findViewById(R.id.coBtn);

        /**
         * Launch SettingsAdminActivity if the authentication is ok, inflate a Toast if not
         */
        coBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectAdminViewModel.Verify(editUser.getText().toString(), editMdp.getText().toString())) {
                    Intent admin;
                    admin = new Intent(ConnecAdminActivity.this, SettingsAdminActivity.class);
                    startActivity(admin);
                } else {
                    Toast.makeText(ConnecAdminActivity.this, "Une erreur se trouve dans le formulaire", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
