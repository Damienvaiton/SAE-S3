package com.example.pageacceuil.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pageacceuil.Model.Data;
import com.example.pageacceuil.Model.ESP;
import com.example.pageacceuil.Model.FirebaseAccess;

import java.util.ArrayList;

public class GraphViewModel extends ViewModel {
FirebaseAccess acess=FirebaseAccess.getInstance();
ESP currentEsp= ESP.getInstance();
/*    public LiveData<Data> getData() {
        MutableLiveData<Data> listener = new MutableLiveData<>();
        acess.setPrechargeDonnee("d");
        listener.postValue(data);

    }*/


    @Override
    protected void onCleared() {
        super.onCleared();
    }
    public LiveData<Data> getData() {
        MutableLiveData<Data> listenerData = new MutableLiveData<>();
        acess.setPrechargeDonnee();
        acess.setRealtimeDataListener();
       /* FirebaseAccess.getInstance().setPrechargeDonnee();
        FirebaseAccess.getInstance().setRealtimeDataListener();*/
        listenerData.postValue(FirebaseAccess.getInstance().getNewData());
        return listenerData;
    }

    public LiveData<String> getTemps() {
        MutableLiveData<String> listenerTemps = new MutableLiveData<>();
        acess.getTimeListener(currentEsp);
       // FirebaseAccess.getInstance().getTimeListener(ESP.getInstance());
        System.out.println("je suis la");
        listenerTemps.postValue(ESP.getInstance().getTauxRafrai());
        return listenerTemps;
    }
}
