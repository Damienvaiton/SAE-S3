package View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pageacceuil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Connection_admin_page extends AppCompatActivity {

    EditText editUser;
    EditText editMdp;
    Button coBtn;

    String user;
    String mdp;

    HashMap<String, String> ESP;
    ArrayList<String> tabESP;

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("SAE_S3_BD/Admin");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_connect_admin);

        editMdp = findViewById(R.id.coMdp);
        editUser = findViewById(R.id.coUsername);
        coBtn = findViewById(R.id.coBtn);
        coBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(user);
                System.out.println(editUser.getText().toString());
                System.out.println(mdp);
                System.out.println(editMdp.getText().toString());
                if (Objects.equals(user, editUser.getText().toString().trim()) && Objects.equals(mdp, editMdp.getText().toString().trim())) {
                    Intent ac;
                    ac = new Intent(Connection_admin_page.this, Admin_page.class);
                    ac.putExtra("listeESP", tabESP);
                    ac.putExtra("hashmapEsp", ESP);
                    startActivity(ac);
                } else {
                    Toast.makeText(getApplicationContext(), "Username ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
            }

        });
        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot tab = task.getResult();
                user = tab.child("Admin").getValue(String.class);
                mdp = tab.child("mdp").getValue(String.class);

            }
        });


    }
}