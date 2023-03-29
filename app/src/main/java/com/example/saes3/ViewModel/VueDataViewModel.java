package com.example.saes3.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.saes3.Model.Data;
import com.example.saes3.Model.DataUpdate;

public class VueDataViewModel extends ViewModel implements DataUpdate {
    public VueDataViewModel() {

        getData().observeForever(data -> System.out.println("dans le vue data"));

        getMoments().observeForever(s -> System.out.println("dd"));
    }

    @Override
    public LiveData<Data> getData(){
        return listenerDonnées;
    }

    public void updateData(Data data) {
        if (data != null) {
            listenerDonnées.postValue(data);
        }
    }

}
