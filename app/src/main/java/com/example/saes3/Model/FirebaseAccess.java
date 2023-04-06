package com.example.saes3.Model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.saes3.AppApplication;
import com.example.saes3.Util.AlertDialog;
import com.example.saes3.Util.ClassTransitoireViewModel;
import com.example.saes3.View.GraphiqueActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseAccess {
    /**
     * Paramètre qui conserve une instance de FirebaseAccess une fois celle-ci créée
     */
    private static FirebaseAccess instance;
    /**
     * Instantiation de Firebase
     */
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = database.getReference("SAE_S3_BD");

    public static final String ESP32 = "ESP32";
    public static final String REFRESH_TIME = "TauxRafraichissement";
    public static final String MEASURE = "Mesure";

    private ClassTransitoireViewModel transitoireViewModel;
public static Long refresh;
    private ESP currentESP;


    /**
     * Listener rattaché au différent champs de Firebase
     */
    private ValueEventListener valueEventListenerTemps;
    private ChildEventListener realtimeDataListener;

    /**
     * Différents setters pour la class FirebaseAccess qui s'occupe de tous les appels à la base de données Firebase.
     */
    public FirebaseAccess() {
        this.transitoireViewModel = ClassTransitoireViewModel.getInstance();
    }

    /**
     * Récupère ou construit l'unique instaance de FirebaseAccess
     *
     * @return instance de la classe FirebaseAccess
     */
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

    /**
     * Setter pour la classe ESP actuel
     *
     * @param esp
     */
    public void setEsp(ESP esp) {
        this.currentESP = esp;
        if (valueEventListenerTemps == null || realtimeDataListener == null) {

        }
    }

    /**
     * Query to the database for load every ESP name
     */
    public void getAllESP() {
        myRef.child(ESP32).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String surnom = (String) snapshot.child("Nom").getValue();
                if (surnom != null) {
                    transitoireViewModel.ajoutESP(snapshot.getKey(), surnom);
                } else {
                    transitoireViewModel.ajoutESP(snapshot.getKey(), null);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // TODO document why this method is empty
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                transitoireViewModel.suppESP(snapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // TODO document why this method is empty
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // TODO document why this method is empty
            }
        });
    }

    /**
     * Query to the databse for change resfreh rate of the current ESP
     *
     * @param temps time in millisecond
     */
    public void setEspRefreshRate(int temps) {// context passé en param
        myRef.child(ESP32).child(currentESP.getMacEsp()).child(REFRESH_TIME).setValue(temps * 1000).addOnSuccessListener(aVoid -> Log.d("Firebase", "Données enregistrées avec succès")).addOnFailureListener(e -> Log.e("Firebase", "Erreur lors de l'enregistrement des données", e));
    }

    /**
     * Query to the database for wipe datas
     */
    public void resetValueFirebase() {
        myRef.child(ESP32).child(currentESP.getMacEsp()).child(MEASURE).removeValue().addOnSuccessListener(aVoid -> Log.d("Firebase", "Données enregistrées avec succès")).addOnFailureListener(e -> {
            AlertDialog.getInstance().errorFirebase();
        });

        myRef.child(ESP32).child(currentESP.getMacEsp()).child("MesureNumber").removeValue().addOnSuccessListener(aVoid -> Log.d("Firebase", "Données enregistrées avec succès")).addOnFailureListener(e -> {
            AlertDialog.getInstance().errorFirebase();
    });
    }

    /**
     * Query to the database for load datas already on
     */
    public boolean loadInData() {
        ListData listData = ListData.getInstance();
        myRef.child(ESP32).child(currentESP.getMacEsp()).child(MEASURE).get().addOnCompleteListener(task -> {
            DataSnapshot tab = task.getResult();
            if (tab.exists()) {
                for (DataSnapshot dataSnapshot : tab.getChildren()) {
                    handleDataSnapshot(dataSnapshot,listData);
                }
            } else if(AppApplication.getCurrentActivity()!=null && AppApplication.getCurrentActivity() instanceof GraphiqueActivity)
            {
                AlertDialog.getInstance().emptyESP();
            }
        });
        return true;
    }

    /**
     * Query to the database to return the refresh time of the current ESP
     */
    public void setEspTimeListener() {
        valueEventListenerTemps = new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String heure = "";
                String minute = "";
                String seconde = "";
                refresh=snapshot.getValue(Long.class);
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
                    transitoireViewModel.updateRefresh(heure+minute+seconde);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if(AppApplication.getCurrentActivity()!=null && AppApplication.getCurrentActivity() instanceof GraphiqueActivity)
                {
                    AlertDialog.getInstance().emptyESP();
            }}
        };
        myRef.child(ESP32).child(ESP.getInstance().getMacEsp()).child(REFRESH_TIME).addValueEventListener(valueEventListenerTemps);
    }
    private void handleDataSnapshot(DataSnapshot dataSnapshot, ListData listData) {
        try {
            Data data = dataSnapshot.getValue(Data.class);
            if (data != null) {
                transitoireViewModel.updateData(data);
                listData.listAddData(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Set a listener to the value data on the database, this  function is triggered each new value add on the database
     */
    public void setRealtimeDataListener() {
        realtimeDataListener = new ChildEventListener() {
            ListData listData = ListData.getInstance();

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // TODO document why this method is empty
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getChildrenCount() == 6) {
                    handleDataSnapshot(snapshot,listData);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // TODO document why this method is empty
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // TODO document why this method is empty
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                AlertDialog.getInstance().errorFirebase();}
        };
        myRef.child(ESP32).child(currentESP.getMacEsp()).child(MEASURE).addChildEventListener(realtimeDataListener);
    }

    /**
     * Query to the database to get admin log
     */
    public String[] getAdminLog() {
        final String[] access = new String[2];
        myRef.child("Admin").get().addOnCompleteListener(task -> {
            DataSnapshot tab = task.getResult();
            access[0] = tab.child("Admin").getValue(String.class);
            access[1] = tab.child("mdp").getValue(String.class);

        });
        return access;
    }

    /**
     * Query to the databse to add a nickname on the current ESP
     *
     * @param nickname new nickname for the ESP
     */
    public void setNicknameEsp(String nickname) {
        myRef.child(ESP32).child(currentESP.getMacEsp()).child("Nom").setValue(nickname);

    }

    /**
     * Query to the database to erase current ESP and all of this values
     */
    public void deleteEsp() {
        myRef.child(ESP32).child(currentESP.getMacEsp()).removeValue();

    }

    /**
     * delete each listener attach to the database
     */
    public boolean deleteListener() {
        myRef.child(ESP32).child(currentESP.getMacEsp()).child(MEASURE).removeEventListener(valueEventListenerTemps);
        myRef.child(ESP32).child(currentESP.getMacEsp()).child(REFRESH_TIME).removeEventListener(realtimeDataListener);
        return true;
    }
public boolean testFirebase() {

    myRef.addListenerForSingleValueEvent(new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            transitoireViewModel.echecFirebase();
            AlertDialog.getInstance().errorFirebase();

        }
    });

    return true;
}
}

