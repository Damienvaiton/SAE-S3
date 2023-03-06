package com.example.pageacceuil.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pageacceuil.Model.Data;
import com.example.pageacceuil.Model.DataUpdate;
import com.example.pageacceuil.Model.FirebaseAccess;
import com.example.pageacceuil.Model.ListData;
import com.example.pageacceuil.View.VueDataActivity;

import java.util.ArrayList;

public class VueDataViewModel extends ViewModel implements DataUpdate {
    private FirebaseAccess firebaseAccess;

    private ArrayList<Data> listData;
    public VueDataViewModel() {
         firebaseAccess=FirebaseAccess.getInstance();

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
