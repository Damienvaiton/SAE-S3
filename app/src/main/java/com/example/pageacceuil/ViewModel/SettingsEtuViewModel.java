package com.example.pageacceuil.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pageacceuil.Model.ESP;
import com.example.pageacceuil.Model.FirebaseAccess;

public class SettingsEtuViewModel extends ViewModel {
    private FirebaseAccess acess;
    private ESP currentEsp;
    private ClassTransitoireViewModel transit;

    public SettingsEtuViewModel() {
        transit=ClassTransitoireViewModel.getInstance();
        transit.setSettingsEtuViewModel(this);
        acess = FirebaseAccess.getInstance();
        currentEsp = ESP.getInstance();


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
