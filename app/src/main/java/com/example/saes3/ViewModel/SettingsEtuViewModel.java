package com.example.saes3.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.saes3.Model.ESP;
import com.example.saes3.Model.FirebaseAccess;

public class SettingsEtuViewModel extends ViewModel {
    private final MutableLiveData<String> listenerTemps = new MutableLiveData<>();
    private FirebaseAccess acess;
    private ESP currentEsp;

    public SettingsEtuViewModel() {
        acess = FirebaseAccess.getInstance();
        currentEsp = ESP.getInstance();
        // Comment récup l'instance de graphViewModel= Pourquoi pas faire une classe qui contient les 2 axe?


    }

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
