package com.example.pageacceuil.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pageacceuil.Model.Data;
import com.example.pageacceuil.Model.DataUpdate;
import com.example.pageacceuil.Model.FirebaseAccess;
import com.example.pageacceuil.View.VueDataActivity;

public class VueDataViewModel extends ViewModel implements DataUpdate {
    FirebaseAccess firebaseAccess;
    VueDataActivity vueDataActivity;
    RecyclerView recyclerView;

    public VueDataViewModel() {

        firebaseAccess=FirebaseAccess.getInstance();
        DataAdapter dataAdapter = new DataAdapter(getApplicationContext());
//vueDataActivity=
      //  RecyclerView recyclerView = findViewById(R.id.recyclerview);
      //  recyclerView.setAdapter(dataAdapter);
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
            listenerDonnées.postValue(data);
        }
    }


    public LiveData<String> getMoments() {
        return listenerTemps;
    }
public void setRecycler(RecyclerView recyclerView){
        this.recyclerView=recyclerView;
}

}
