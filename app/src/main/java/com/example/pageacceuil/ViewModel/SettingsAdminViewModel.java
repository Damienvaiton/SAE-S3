package com.example.pageacceuil.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pageacceuil.Model.Data;
import com.example.pageacceuil.Model.FirebaseAccess;
import com.example.pageacceuil.Model.ListData;

import java.util.ArrayList;

public class SettingsAdminViewModel extends ViewModel {
    private FirebaseAccess database=FirebaseAccess.getInstance();
    public LiveData<ArrayList<ListData>> getDataAdmin() {
        MutableLiveData<ArrayList<ListData>> listenerDataAdmin = new MutableLiveData<>();
            MutableLiveData<Data> listenerData = new MutableLiveData<>();
            database.loadInData();
        //   listenerData.postValue(FirebaseAccess.getInstance().getNewData());
            return listenerDataAdmin;
        }

    public LiveData<String> getTempsAdmin() {
        MutableLiveData<String> listenerTempsAdmin = new MutableLiveData<>();
        //listenerTempsAdmin.postValue(currentEsp.getTauxRafrai());
        return listenerTempsAdmin;
    }
 public void renameESP(String nickname){
        database.setNicknameEsp(nickname);
 }
    public void suppESP(){
        database.deleteEsp();
    }

    public void resetESP(){
        database.resetValueFirebase();
    }


 public void getDonnées(){
        database.loadInData();
 }


}
