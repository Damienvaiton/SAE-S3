package com.example.pageacceuil.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public interface DataUpdate {
    final MutableLiveData<Data> listenerDonnées = new MutableLiveData<>();
     final MutableLiveData<String> listenerTemps = new MutableLiveData<>();

    //faire get et update, mais comment signiflier?

}
