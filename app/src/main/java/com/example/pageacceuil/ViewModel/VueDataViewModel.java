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
    FirebaseAccess firebaseAccess;
    VueDataActivity vueDataActivity;
    dataRecyclerAdapter dataRecyclerAdapter;
    ArrayList<Data> listData;
    public VueDataViewModel() {
        listData=new ArrayList<>();
        ListData a=ListData.getInstance();
       listData.add(a.recup_data(1));
        listData.add(a.recup_data(2));
        listData.add(a.recup_data(3));
        listData.add(a.recup_data(4));
         firebaseAccess=FirebaseAccess.getInstance();
         dataRecyclerAdapter = new dataRecyclerAdapter(getApplicationContext(),listData);

    }

   // public LiveData<Boolean>
    private Context getApplicationContext() {
        return getApplicationContext();
    }

    public LiveData<Data> getData(){
        return listenerDonnées;
    }
    public void updateData(Data data) {
        if (data != null) {
            dataRecyclerAdapter.notifyDataSetChanged();
            listenerDonnées.postValue(data);
        }
    }

    public dataRecyclerAdapter getDataRecyclerAdapter() {
        return dataRecyclerAdapter;
    }
}
