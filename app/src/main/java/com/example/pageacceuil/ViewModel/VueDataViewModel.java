package com.example.pageacceuil.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.pageacceuil.Model.Data;
import com.example.pageacceuil.Model.DataUpdate;
import com.example.pageacceuil.Model.FirebaseAccess;

import java.util.ArrayList;

public class VueDataViewModel extends ViewModel implements DataUpdate {
    private FirebaseAccess firebaseAccess;

    private ArrayList<Data> listData;
    public VueDataViewModel() {
        firebaseAccess = FirebaseAccess.getInstance();
        getData().observeForever(new Observer<Data>() {
            @Override
            public void onChanged(Data data) {
                System.out.println("dans le vue data");
            }
        });

        getMoments().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                System.out.println("dd");
            }
        });
    }


    public LiveData<Data> getData(){
        return listenerDonnées;
    }
    public void updateData(Data data) {
        if (data != null) {
            listenerDonnées.postValue(data);
        }
    }

}
