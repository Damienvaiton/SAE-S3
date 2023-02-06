package ViewModel;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;

import java.util.Objects;

public class MainViewModel extends ViewModel {
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
}
