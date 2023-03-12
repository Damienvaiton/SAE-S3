package com.example.pageacceuil.ViewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.pageacceuil.Model.Data;
import com.example.pageacceuil.Model.ESP;
import com.example.pageacceuil.Model.FirebaseAccess;
import com.example.pageacceuil.Model.ListData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SettingsAdminViewModel extends ViewModel {
    private HashMap<String, String> hashmapESP;
    private FirebaseAccess database=FirebaseAccess.getInstance();
    private MutableLiveData<Data> recyclerListener;
    public SettingsAdminViewModel() {

        database = FirebaseAccess.getInstance();
        database.setSettingsAdminViewModel(this);
        database.getAllESP();
        hashmapESP = new HashMap<String, String>();
    }
    public void setListenerESP(String value) {
        hashmapESP.get(value);
        database.setRealtimeDataListener();
        database.loadInData();
        database.setEspTimeListener();
    }

    public LiveData<Data> updateRecycler() {
        return recyclerListener;
    }
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



        MutableLiveData<ArrayList<String>> listener = new MutableLiveData<>();
        ArrayList<String> tabESP = new ArrayList<>();

        public LiveData<ArrayList<String>> getHashmapESP() {
            return listener;
        }

        public void addESP(String esp, @Nullable String nom) {
            if (nom == null) {
                hashmapESP.putIfAbsent(esp, null);
                tabESP.add(esp);
            } else {
                hashmapESP.put(esp, nom);
                tabESP.add(nom);
            }
            listener.postValue(tabESP);
        }

        public void deleteESP(String esp) {
            for (String s : tabESP) {
                if (s.equals(esp)) {
                    tabESP.remove(s);
                }
                listener.postValue(tabESP);
            }
        }

        public void creaESP(int pos) {
            int curseur = 0;
            for (Map.Entry<String, String> entry : hashmapESP.entrySet()) {
                if (curseur == pos) {
                    if (entry.getValue() == null) {
                        database.setEsp(new ESP(entry.getKey(), null));
                        return;
                    }
                    database.setEsp(new ESP(entry.getKey(), null));
                    return;
                }curseur++;
            }
        }

        public void changeESP(){
            database.deleteListener();
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
    public void setESPrefresh(String temps){
            database.setEspRefreshRate(Integer.parseInt(temps));
    }


 public void getDonnées(){
        database.loadInData();
 }


}
