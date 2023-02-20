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
        acess.setGraphViewModel(this);
        acess.setPrechargeDonnee();
        acess.setRealtimeDataListener();
        acess.setTimeListener(currentEsp);
    }

    private final MutableLiveData<Data> listenerData = new MutableLiveData<>();
    private final MutableLiveData<String> listenerTemps = new MutableLiveData<>();
    public LiveData<Data> getData() {
        return listenerData;
    }

    public void updateData(Data data) {
        System.out.println("update data");
        listenerData.postValue(data);
    }

  /*  public LiveData<Data> getData(Data data) {
        MutableLiveData<Data> listenerData = new MutableLiveData<>();
        listenerData.postValue(data);
        System.out.println("c mihswecs<");
        return listenerData;
    }*/
    public LiveData<String> getTemps() {
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
