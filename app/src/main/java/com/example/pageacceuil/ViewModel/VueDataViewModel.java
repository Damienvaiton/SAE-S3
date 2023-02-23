package com.example.pageacceuil.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pageacceuil.Model.Data;
import com.example.pageacceuil.Model.DataUpdate;
import com.example.pageacceuil.Model.FirebaseAccess;

public class VueDataViewModel extends ViewModel implements DataUpdate {
    FirebaseAccess firebaseAccess;
    public VueDataViewModel() {
        firebaseAccess=FirebaseAccess.getInstance();
    }

    private final MutableLiveData<Data> listenerData = new MutableLiveData<>();

    public void updateData(Data data){
        listenerData.postValue(data);
    }

    @Override
    public void updateMoments() {

    }

    public LiveData<Data> getData(){
        return listenerDonnées;
    }

    @Override
    public LiveData<String> getMoments() {
        return null;
    }
}
