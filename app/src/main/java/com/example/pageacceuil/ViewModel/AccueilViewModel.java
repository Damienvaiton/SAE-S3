package com.example.pageacceuil.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AccueilViewModel extends ViewModel {

    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("SAE_S3_BD/ESP32");

    public LiveData<ArrayList<String>> getESP() {

        // je déclare l'observer
        MutableLiveData<ArrayList<String>> listener = new MutableLiveData<>();

        //je fais le traitement souhaité
        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot tab = task.getResult();
                ArrayList<String> tabESP=new ArrayList<>();
                for (DataSnapshot child : tab.getChildren()) {
                    tabESP.add(child.getKey());
                }
                listener.postValue(tabESP);

              /*  String[] id= new String[2];
                    id[0] = tab.child("Admin").getValue(String.class);
                    id[1] = tab.child("mdp").getValue(String.class);

                    // je notifie les observateurs que les données sont dispos
                    listener.postValue(id);*/
            }
        });

        // je retourne l'observer qui est vide au début puis se "déclenche"
        // quand les données sont récupérées.
        return listener;
    }

}
