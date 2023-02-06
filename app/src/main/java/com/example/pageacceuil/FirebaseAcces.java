package com.example.pageacceuil;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseAcces {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = database.getReference("SAE_S3_BD");

    private static volatile FirebaseAcces instance;

    public static FirebaseAcces getInstance() {

        FirebaseAcces result = instance;
        if (result != null) {
            return result;
        }
        synchronized (FirebaseAcces.class) {
            if (instance == null) {
                instance = new FirebaseAcces();
            }
            return instance;
        }
    }


    public boolean editTemps(String choixESP, int values) {
        if (myRef.child("ESP32").child(choixESP).child("TauxRafraichissement").setValue(values * 1000).isSuccessful()) {
            return true;
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

    public Long getTimeListener(String choixESP) {
        Long time = Long.valueOf(0);
        myRef.child(choixESP).child("TauxRafraichissement").addValueEventListener(new ValueEventListener() {
Long time= Long.valueOf(0);
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long heure = Long.valueOf(0);
                Long minute = Long.valueOf(0);
                Long seconde = Long.valueOf(0);
                if (snapshot.exists()) {


                    if (snapshot.getValue(Long.class) >= 3600000) {
                        heure = (snapshot.getValue(Long.class) / (1000 * 60 * 60) );
                    }
                    if (snapshot.getValue(Long.class) >= 60000) {
                        minute = (snapshot.getValue(Long.class) % (1000 * 60 * 60)) / (1000 * 60) ;
                    }
                    if (snapshot.getValue(Long.class) >= 1000) {
                        seconde = (snapshot.getValue(Long.class) % (1000 * 60)) / 1000 ;
                    }
                    time = heure + minute + seconde;
//                    ESP.set

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return time;
    }
//    public boolean deleteListener(String choixESP, String champs) {
//        myRef.child("ESP32").child(choixESP).child(champs).removeEventListener(valueEventListenerDate);
//    }
}
