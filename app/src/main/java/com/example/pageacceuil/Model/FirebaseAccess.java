package com.example.pageacceuil.Model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pageacceuil.ViewModel.ClassTransitoireViewModel;
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

public class FirebaseAccess implements DataUpdate {
    /**
     * Instantiation de Firebase
     */
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference myRef = database.getReference("SAE_S3_BD");

    /**
     * Différent ViewModel
     */
    private ClassTransitoireViewModel transitoireViewModel;

    public FirebaseAccess() {
        this.transitoireViewModel = ClassTransitoireViewModel.getInstance();
    }

    private ESP currentESP;

    final static String nameColumnESP = "ESP32";

    final static String nameColumnRefresh = "TauxRafraichissement";

    final static String nameColumnMesure = "Mesure";
    /**
     * Listener rattaché au différent champs de Firebase
     */

    private ValueEventListener valueEventListenerTemps;
    private ChildEventListener realtimeDataListener;

    /**
     * Paramètre qui conserve une instance de FirebaseAccess une fois celle-ci créée
     */
    private static FirebaseAccess instance;

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
        if(valueEventListenerTemps==null||realtimeDataListener==null ){
            deleteListener();
        }
    }

    /**
     * Query to the database for load every ESP name
     */
    public void getAllESP() {
        myRef.child(nameColumnESP).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String surnom = (String) snapshot.child("Nom").getValue();
                if (surnom != null) {
                    transitoireViewModel.ajoutESP(snapshot.getKey(),surnom);
                } else {
                    transitoireViewModel.ajoutESP(snapshot.getKey(), null);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                transitoireViewModel.suppESP(snapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Query to the databse for change resfreh rate of the current ESP
     *
     * @param temps time in millisecond
     */
    public void setEspRefreshRate(int temps) {// context passé en param
        myRef.child(nameColumnESP).child(currentESP.getMacEsp()).child(nameColumnRefresh).setValue(temps * 1000)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firebase", "Données enregistrées avec succès");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firebase", "Erreur lors de l'enregistrement des données", e);
                    }
                });
    }
    /**
     * Query to the database for wipe datas
     */
    public void resetValueFirebase() {
        myRef.child(nameColumnESP).child(currentESP.getMacEsp()).child(nameColumnMesure).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firebase", "Données enregistrées avec succès");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firebase", "Erreur lors de l'enregistrement des données", e);
                    }
                });
        myRef.child(nameColumnESP).child(currentESP.getMacEsp()).child(nameColumnMesure).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Firebase", "Données enregistrées avec succès");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firebase", "Erreur lors de l'enregistrement des données", e);
                    }
                });
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
                    System.out.println(heure+minute+seconde);
                    transitoireViewModel.updateRefresh(String.valueOf(5));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Erreur lors de l'enregistrement des données : " + error);

            }
        };
        myRef.child(nameColumnESP).child(ESP.getInstance().getMacEsp()).child(nameColumnRefresh).addValueEventListener(valueEventListenerTemps);
    }

    /**
     * Query to the database for load datas already on
     */
    public boolean loadInData() {
        ListData listData = ListData.getInstance();
        myRef.child(nameColumnESP).child(currentESP.getMacEsp()).child(nameColumnMesure).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                DataSnapshot tab = task.getResult();
                if (tab.exists()) {
                    for (DataSnapshot dataSnapshot : tab.getChildren()) {
                        System.out.println("je passe");
                        transitoireViewModel.updateData(dataSnapshot.getValue(Data.class));
                        listData.list_add_data(dataSnapshot.getValue(Data.class));


                    }
                    updateLiveTabData(listData.getListAllData());
                } else {
                    System.out.println("Impossible d'accéder au données précharge");
                }
            }

        });

        return true;
    }
    /**
     * Set a listener to the value data on the database, this  function is triggered each new value add on the database
     */
    public void setRealtimeDataListener() {
        realtimeDataListener = new ChildEventListener() {
            ListData listData = ListData.getInstance();

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getChildrenCount() == 6) {
                    transitoireViewModel.updateData(snapshot.getValue(Data.class));
                    listData.list_add_data(snapshot.getValue(Data.class));
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
        myRef.child(nameColumnESP).child(currentESP.getMacEsp()).child(nameColumnMesure).addChildEventListener(realtimeDataListener);

    }


    /**
     * Query to the database to get admin log
     */
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

    /**
     * Query to the databse to add a nickname on the current ESP
     * @param nickname new nickname for the ESP
     */
    public void setNicknameEsp(String nickname) {
        myRef.child(nameColumnESP).child(currentESP.getMacEsp()).child("Nom").setValue(nickname);

    }

    /**
     * Query to the database to erase current ESP and all of this values
     */
    public void deleteEsp() {
        myRef.child(nameColumnESP).child(currentESP.getMacEsp()).removeValue();

    }

    /**
     * delete each listener attach to the database
     */
    public boolean deleteListener() {
        if(valueEventListenerTemps!=null && realtimeDataListener!=null) {
            myRef.child(nameColumnESP).child(currentESP.getMacEsp()).child(nameColumnMesure).removeEventListener(valueEventListenerTemps);
            myRef.child(nameColumnESP).child(currentESP.getMacEsp()).child(nameColumnRefresh).removeEventListener(realtimeDataListener);
        }
        return true;
    }


    }
