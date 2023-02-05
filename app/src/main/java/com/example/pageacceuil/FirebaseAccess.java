package com.example.pageacceuil;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseAccess {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = database.getReference("SAE_S3_BD");

    ValueEventListener valueEventListenerTemps;

    ValueEventListener valueEventListenerData;

    private static volatile FirebaseAccess instance;

    public static FirebaseAccess getInstance() {

        FirebaseAccess result = instance;
        if (result != null) {
            return result;
        }
        synchronized (FirebaseAccess.class) {
            if (instance == null) {
                instance = new FirebaseAccess();
            }
            return instance;
        }
    }


    public boolean editTemps(String choixESP, int values , Context context) {// context passé en param
        myRef.child("ESP32").child(choixESP).child("TauxRafraichissement").setValue(values * 1000)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Refresh : " + values + "s,\r\nVous pouvez redémarrer l'ESP", Toast.LENGTH_SHORT).show();
                      //  Snackbar.make(, "yo",Snackbar.LENGTH_SHORT).show();
                        Log.d("Firebase", "Données enregistrées avec succès");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Erreur : " +e, Toast.LENGTH_SHORT).show();
                        Log.e("Firebase", "Erreur lors de l'enregistrement des données", e);
                    }
                });{

        }
        return false;
    } //test isSucessfull marche pas

    public boolean resetValueDb(String choixESP) {
        if ((myRef.child("ESP32").child(choixESP).child("Mesure").removeValue().isSuccessful()) && (myRef.child("ESP32").child(choixESP).child("MesureNumber").removeValue().isSuccessful())) {
            return true;
        }
        return false;
    }

    public boolean prechargebd(String choixESP) {
        ListData listData = ListData.getInstance();
        myRef.child("ESP32").child(choixESP).child("Mesure").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot tab = task.getResult();
                if (tab.exists()) {

                    for (int i = 0; i < tab.getChildrenCount(); i++) {
                        Data a = tab.child(i + "").getValue(Data.class);
                        listData.list_add_data(a);
                    }
                    //Faire toute mes requetes ici et listener
                }
            }

        });
        if (listData.list_size() != 0) {
            return true;
        }
        System.out.println("Impossible d'accéder au données");
        return false;
    }

    public void getTimeListener(ESP currentESP) {
        valueEventListenerTemps = new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String heure = "";
                String minute = "";
                String seconde = "";
                if (snapshot.exists()) {


                    if (snapshot.getValue(Long.class) >= 3600000) {
                        heure = (snapshot.getValue(Long.class) / (1000 * 60 * 60)) + "h";
                    }
                    if (snapshot.getValue(Long.class) >= 60000) {
                        minute = (snapshot.getValue(Long.class) % (1000 * 60 * 60) / (1000 * 60)) + "m";
                    }
                    if (snapshot.getValue(Long.class) >= 1000) {
                        seconde = (snapshot.getValue(Long.class) % (1000 * 60)) / 1000 + "s";
                    }
                    System.out.println(seconde + "oooo");
                    System.out.println(heure + minute + seconde + "yoooooooooooooooooo");
                    currentESP.tauxRafrai = heure + minute + seconde;
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        myRef.child("ESP32").child(currentESP.macEsp).child("TauxRafraichissement").addValueEventListener(valueEventListenerTemps);
        return;
    }

    public void getDataListener(ESP currentESP,ListData list){
        valueEventListenerData = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    list.list_add_data(dataSnapshot.getValue(Data.class));
                }
                /*dataAdapter.notifyDataSetChanged();
                recyclerView.invalidate();*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        myRef.child("ESP32").child(currentESP.getMacEsp()).child("Mesure").addValueEventListener(valueEventListenerData);
    }
//    public boolean deleteListener(String choixESP, String champs) {
//        myRef.child("ESP32").child(choixESP).child(champs).removeEventListener(valueEventListenerDate);
//    }
}
