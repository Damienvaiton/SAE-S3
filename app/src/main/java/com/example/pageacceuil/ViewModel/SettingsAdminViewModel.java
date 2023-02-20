package com.example.pageacceuil.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pageacceuil.Model.Data;
import com.example.pageacceuil.Model.FirebaseAccess;
import com.example.pageacceuil.Model.ListData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class SettingsAdminViewModel extends ViewModel {
    FirebaseAccess database=FirebaseAccess.getInstance();
    public LiveData<ArrayList<ListData>> getDataAdmin() {
        MutableLiveData<ArrayList<ListData>> listenerDataAdmin = new MutableLiveData<>();
            MutableLiveData<Data> listenerData = new MutableLiveData<>();
            database.setPrechargeDonnee();
        //   listenerData.postValue(FirebaseAccess.getInstance().getNewData());
            return listenerDataAdmin;
        }

    public LiveData<String> getTempsAdmin() {
        MutableLiveData<String> listenerTempsAdmin = new MutableLiveData<>();
        //listenerTempsAdmin.postValue(currentEsp.getTauxRafrai());
        return listenerTempsAdmin;
    }
  // public ChangeTime(int time){
    //    if ()
   //}
   //public renameESP(){}


}
