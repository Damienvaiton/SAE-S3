package com.example.pageacceuil.Model;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.pageacceuil.ViewModel.AccueilViewModel;
import com.example.pageacceuil.ViewModel.GraphViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseAccess {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = database.getReference("SAE_S3_BD");

    ValueEventListener valueEventListenerTemps;
    ChildEventListener RealtimeDataListener;


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


    public void editTemps(String choixESP, int values, Context context) {// context passé en param
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
                        Toast.makeText(context, "Erreur : " + e, Toast.LENGTH_SHORT).show();
                        Log.e("Firebase", "Erreur lors de l'enregistrement des données", e);
                    }
                });
    } //test isSucessfull marche pas

   /* public String getSurnom(String macESP){
        myRef.child("ESP32").child(macESP).child("Nom").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
        if(myRef.child("ESP32").child(macESP).child("Nom").getKey()){

        return myRef.child("ESP32").child(macESP).child("Nom").getKey();
    }*/
   MutableLiveData<ArrayList<String>> listener = new MutableLiveData<>();

    public void resetValueDb(String choixESP, Context context) {
        myRef.child("ESP32").child(choixESP).child("Mesure").removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //  Snackbar.make(, "yo",Snackbar.LENGTH_SHORT).show();
                        Log.d("Firebase", "Données enregistrées avec succès");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Erreur base de données", Toast.LENGTH_SHORT).show();
                        Log.e("Firebase", "Erreur lors de l'enregistrement des données", e);
                        return;
                    }
                });
        myRef.child("ESP32").child(choixESP).child("Mesure").removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firebase", "Données enregistrées avec succès");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Erreur base de données", Toast.LENGTH_SHORT).show();
                        Log.e("Firebase", "Erreur lors de l'enregistrement des données", e);
                        return;
                    }
                });
        Toast.makeText(context, "Données correctement supprimé", Toast.LENGTH_SHORT).show();
    }


    public boolean setPrechargeDonnee() {
        ESP currentESP = ESP.getInstance();
        ListData listData = ListData.getInstance();

        myRef.child("ESP32").child(currentESP.getMacEsp()).child("Mesure").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot tab = task.getResult();
                if (tab.exists()) {
                    for (int i = 0; i < tab.getChildrenCount(); i++) {
                        listData.list_add_data(tab.child(i + "").getValue(Data.class));
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
                Log.e("Firebase", "Erreur lors de l'enregistrement des données : "+error);

            }
        };
        myRef.child("ESP32").child(currentESP.macEsp).child("TauxRafraichissement").addValueEventListener(valueEventListenerTemps);
        return;
    }
/*
    public void setDataListener(ESP currentESP, ListData list) {
        valueEventListenerData = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    list.list_add_data(dataSnapshot.getValue(Data.class));
                }
                *//*dataAdapter.notifyDataSetChanged();
                recyclerView.invalidate();*//*

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        myRef.child("ESP32").child(currentESP.getMacEsp()).child("Mesure").addValueEventListener(valueEventListenerData);
    }*/

    public void setRealtimeDataListener() {
        ESP currentESP=ESP.getInstance();
        RealtimeDataListener = new ChildEventListener() {
            ListData listData = ListData.getInstance();

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getChildrenCount() == 6) {
                    listData.list_add_data(snapshot.getValue(Data.class));

                    //chargerDonner();
                    //actuValues();

                }
            }


            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Impossible d'accéder au données");
            }
        };
        myRef.child("ESP32").child(currentESP.getMacEsp()).child("Mesure").addChildEventListener(RealtimeDataListener);
    }

    public Data getNewData(){
        ListData listData = ListData.getInstance();
//        return listData.recup_data(i);
        Data a=new Data(23,23,23,23,23,"efdz");
        return a;
    }
    public String[] getAdminLog() {
        final String[] access = new String[2];
        myRef.child("Admin").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot tab = task.getResult();
                access[0] = tab.child("Admin").getValue(String.class);
                access[1] = tab.child("mdp").getValue(String.class);

            }
        });
        return access;
    }

    public boolean deleteListener(String choixESP) {
        myRef.child("ESP32").child(choixESP).child("Mesure").removeEventListener(valueEventListenerTemps);
        myRef.child("ESP32").child(choixESP).child("TauxRafraichissement").removeEventListener(RealtimeDataListener);
        return true;
    }
}
