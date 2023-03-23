package com.example.pageacceuil.ViewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pageacceuil.Model.ESP;
import com.example.pageacceuil.Model.FirebaseAccess;
import com.example.pageacceuil.View.AccueilActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AccueilViewModel extends ViewModel {
    /**
     * Hashmap who constains mac adress of ESP has key and nickname has value if he has one
     */
    private HashMap<String, String> ESP;
    private FirebaseAccess acess;
    private ClassTransitoireViewModel transit;

    /**
     * Constructor who initialiaze the viewModel
     */
    public AccueilViewModel() {

       acess = FirebaseAccess.getInstance();
        acess.setAccueilViewModel(this);
        acess.getAllESP();
        transit=ClassTransitoireViewModel.getInstance();
        transit.setAccueilViewModel(this);
        ESP = new HashMap<String, String>();
    }

    /**
     * LiveData who pass tabESP to the view
     */
    MutableLiveData<ArrayList<String>> listener = new MutableLiveData<>();
    /**
     *ArrayList wich contains nickname of each ESP if it has, mac adress if not
     */
    ArrayList<String> tabESP = new ArrayList<>();
    /**
     * Getter of LiveData object who contains tabESP
     */
    public LiveData<ArrayList<String>> getESP() {
        return listener;
    }
    /**
     * Function trigger if a new ESP has been add to the databse
     * Add value to hashmap ESP and name to tabESP if he has one, macAdress if not
     */

    public void addESP(String esp, @Nullable String nom) {
        System.out.println("addESP");
        if (nom == null) {
            ESP.putIfAbsent(esp, null);
            tabESP.add(esp);
        } else {
            ESP.put(esp, nom);
            tabESP.add(nom);
        }
        listener.postValue(tabESP);
    }
    /**
     *Function trigger if a ESP has been delete to the databse
     * Remove the ESP name of tabESP if @param nameESP has been find in tabESP
     */
    public void deleteESP(String nameESP) {
        for (String s : tabESP) {
            if (s.equals(nameESP)) {
                tabESP.remove(s);
            }
            listener.postValue(tabESP);
        }
    }
/**
 * Define the selected ESP to FirebaseAcess
 */
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

    public void pause(){
        transit.setAccueilViewModel(null);
    }
    public void setInstance(){
        transit.setAccueilViewModel(this);
    }

}