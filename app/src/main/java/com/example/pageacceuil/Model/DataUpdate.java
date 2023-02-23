package com.example.pageacceuil.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public interface DataUpdate {
    final MutableLiveData<Data> listenerDonnées = new MutableLiveData<>();
     final MutableLiveData<String> listenerTemps = new MutableLiveData<>();

    public LiveData<Data> getData();
    public LiveData<String> getMoments();

    public void updateData(Data data);

    public void updateMoments();







}
