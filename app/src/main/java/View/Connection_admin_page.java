package View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pageacceuil.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import ViewModel.MainViewModel;

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

    //déclaration du viewmodel
    private MainViewModel mainViewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_connect_admin);

        // on demande au système de créer l'instance de Mainviewmodel si elle existe pas et de
        // lier son cycle de vie à l'activity courrante
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

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
                    ac = new Intent(Connection_admin_page.this, AdminActivity.class);
                    ac.putExtra("listeESP", tabESP);
                    ac.putExtra("hashmapEsp", ESP);
                    startActivity(ac);
                } else {
                    Toast.makeText(getApplicationContext(), "Username ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
            }

        });

        // je demande au VM de me donner le user. Peut importe si il est en bdd etc ...
        //l'idéal serait de ne pas recevoir de DataSnapShot
        // On Observe le resultat grace au livedata qui est un conteneur qui permet d'observer
        mainViewModel.getUser().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                user = dataSnapshot.child("Admin").getValue(String.class);
                mdp = dataSnapshot.child("mdp").getValue(String.class);
            }
        });
//n'est plus util
//        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                DataSnapshot tab = task.getResult();
//                user = tab.child("Admin").getValue(String.class);
//                mdp = tab.child("mdp").getValue(String.class);
//
//            }
//        });


    }
}