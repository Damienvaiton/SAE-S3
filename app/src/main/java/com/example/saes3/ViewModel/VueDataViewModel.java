package com.example.saes3.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.saes3.Model.Data;

public class VueDataViewModel extends ViewModel{



       // getMoments().observeForever(s -> System.out.println("dd"));
        MutableLiveData<Boolean> listenerDonnées=new MutableLiveData<>(true);


        public LiveData getData(){
        return listenerDonnées;
    }

    public void updateData(Data data) {
        if (data != null) {
            listenerDonnées.postValue(null);
        }
    }

    }
