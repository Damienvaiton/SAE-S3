package com.example.pageacceuil.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public interface DataUpdate {

    MutableLiveData<Data> listenerDonnées = new MutableLiveData<>();
     MutableLiveData<String> listenerTemps = new MutableLiveData<>();

 public default LiveData<Data> getData(){

     return listenerDonnées;
 }
    //faire get et update, mais comment signiflier?

}
