package com.example.pageacceuil.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pageacceuil.Model.Data;
import com.example.pageacceuil.Model.ESP;
import com.example.pageacceuil.Model.FirebaseAccess;

import java.util.ArrayList;

public class GraphViewModel extends ViewModel {
FirebaseAccess acess;
ESP currentEsp;

    public GraphViewModel() {
        this.acess =FirebaseAccess.getInstance();
        this.currentEsp =ESP.getInstance();
        acess.setPrechargeDonnee();
        acess.setRealtimeDataListener();
        acess.setTimeListener(currentEsp);
    }


    public LiveData<Data> getData(Data data) {
        MutableLiveData<Data> listenerData = new MutableLiveData<>();
        listenerData.postValue(data);
        return listenerData;
    }
    public LiveData<String> getTemps() {
        MutableLiveData<String> listenerTemps = new MutableLiveData<>();
        listenerTemps.postValue(currentEsp.getTauxRafrai());
        return listenerTemps;
    }
    @Override
    protected void onCleared() {
        super.onCleared();
    }
   /* public LiveData<Data> getData() {
        MutableLiveData<Data> listenerData = new MutableLiveData<>();
        acess.setPrechargeDonnee();
        acess.setRealtimeDataListener();
       *//* FirebaseAccess.getInstance().setPrechargeDonnee();
        FirebaseAccess.getInstance().setRealtimeDataListener();*//*
        listenerData.postValue(FirebaseAccess.getInstance().getNewData());
        return listenerData;
    }*/


}
