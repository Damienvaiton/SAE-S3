package com.example.pageacceuil.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pageacceuil.Model.ESP;
import com.example.pageacceuil.Model.FirebaseAccess;

public class SettingsEtuViewModel extends ViewModel {
    private FirebaseAccess acess;
    private ESP currentEsp;

    public SettingsEtuViewModel() {
        acess = FirebaseAccess.getInstance();
        currentEsp = ESP.getInstance();
        // Comment récup l'instance de graphViewModel= Pourquoi pas faire une classe qui contient les 2 axe?


    }

    private final MutableLiveData<String> listenerTemps = new MutableLiveData<>();

    public void updateMoments() {
       // listenerTemps.postValue(currentEsp.getTauxRafrai());
    }

    public LiveData<String> getMoments() {
        return listenerTemps;
    }

    public boolean editTemps(int values) {
        acess.setEspRefreshRate(values);
        return true;

    }


}