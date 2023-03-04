package com.example.pageacceuil.Model;

import androidx.lifecycle.MutableLiveData;

public interface DataUpdate {

    MutableLiveData<Data> listenerDonnées = new MutableLiveData<>();
     MutableLiveData<String> listenerTemps = new MutableLiveData<>();

    //faire get et update, mais comment signiflier?

}
