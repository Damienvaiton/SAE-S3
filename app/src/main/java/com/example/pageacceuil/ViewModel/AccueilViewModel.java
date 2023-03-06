package com.example.pageacceuil.ViewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pageacceuil.Model.ESP;
import com.example.pageacceuil.Model.FirebaseAccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AccueilViewModel extends ViewModel {
    private HashMap<String, String> ESP;
    private FirebaseAccess acess;

    public AccueilViewModel() {

       acess = FirebaseAccess.getInstance();
        acess.setAccueilViewModel(this);
        acess.getAllESP();
        ESP = new HashMap<String, String>();
    }

    MutableLiveData<ArrayList<String>> listener = new MutableLiveData<>();
    ArrayList<String> tabESP = new ArrayList<>();

    public LiveData<ArrayList<String>> getESP() {

        // je déclare l'observer


        //je fais le traitement souhaité


              /*  String[] id= new String[2];
                    id[0] = tab.child("Admin").getValue(String.class);
                    id[1] = tab.child("mdp").getValue(String.class);

                    // je notifie les observateurs que les données sont dispos
                    listener.postValue(id);*/


        // je retourne l'observer qui est vide au début puis se "déclenche"
        // quand les données sont récupérées.
        return listener;
    }

    public void addESP(String esp, @Nullable String nom) {
        if (nom == null) {
            ESP.putIfAbsent(esp, null);
            tabESP.add(esp);
        } else {
            ESP.put(esp, nom);
            tabESP.add(nom);
        }
        listener.postValue(tabESP);
    }

    public void deleteESP(String esp) {
        for (String s : tabESP) {
            if (s.equals(esp)) {
                tabESP.remove(s);
            }
            listener.postValue(tabESP);
        }
    }

    public void creaESP(int pos) {
        int curseur = 0;
        for (Map.Entry<String, String> entry : ESP.entrySet()) {
            if (curseur == pos) {
                if (entry.getValue() == null) {
                    acess.setEsp(new ESP(entry.getKey(), null));
                    return;
                }
                acess.setEsp(new ESP(entry.getKey(), null));
                return;
            }curseur++;
        }


    }
}
