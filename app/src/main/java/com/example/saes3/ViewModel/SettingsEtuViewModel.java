package com.example.saes3.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.saes3.Model.FirebaseAccess;
import com.example.saes3.Util.ClassTransitoireViewModel;

public class SettingsEtuViewModel extends ViewModel {

    private FirebaseAccess acess;

    private ClassTransitoireViewModel transit;

    public SettingsEtuViewModel() {
        transit = ClassTransitoireViewModel.getInstance();
        transit.setSettingsEtuViewModel(this);
        acess = FirebaseAccess.getInstance();
        acess.setEspTimeListener();
    }

    public boolean editTemps(int values) {
        acess.setEspRefreshRate(values);
        return true;

    }

    private final MutableLiveData<String> updateTemps = new MutableLiveData<>();

    public void updateRefresh(String refresh) {
        updateTemps.postValue(refresh);
    }

    public LiveData<String> getMoments() {
        return updateTemps;
    }

}
