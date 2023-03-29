package com.example.pageacceuil.ViewModel;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableList;
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
    private FirebaseAccess database;

    private ClassTransitoireViewModel transit;

    MutableLiveData<String> listenerTempsAdmin = new MutableLiveData<>();
    MutableLiveData<ArrayList<ListData>> listenerDataAdmin = new MutableLiveData<>();

    MutableLiveData<ArrayList<String>> listener = new MutableLiveData<>();

    MutableLiveData<Data> listenerData = new MutableLiveData<>();



    public SettingsAdminViewModel() {

        database = FirebaseAccess.getInstance();

        transit=ClassTransitoireViewModel.getInstance();
        transit.setSettingsAdminViewModel(this);

        database.getAllESP();
        hashmapESP = new HashMap<>();

        ListData.getInstance().getListAllData().addOnListChangedCallback(new ObservableList.OnListChangedCallback() {
            @Override
            public void onChanged(ObservableList sender) {

            }

            @Override
            public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {

            }

            @Override
            public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                listenerData.postValue((Data) sender.get(positionStart));
            }

            @Override
            public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {

            }

            @Override
            public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {

            }
        });
    }
    public void setListenerESP() {
      //  ESP.getInstance().redefinition(hashmapESP.get(value));
        database.setRealtimeDataListener();
        database.loadInData();
        database.setEspTimeListener();
    }
    private final MutableLiveData<String> liveDataRefresh = new MutableLiveData<>();

    public LiveData<Data> getListenerData(){return listenerData;}
    public void updateRefresh(String refresh){
        liveDataRefresh.postValue(refresh);
    }


    public LiveData<ArrayList<ListData>> getDataAdmin() {
        ;
            database.loadInData();
        //   listenerData.postValue(FirebaseAccess.getInstance().getNewData());
            return listenerDataAdmin;
        }

    public LiveData<String> getTempsAdmin() {

        //listenerTempsAdmin.postValue(currentEsp.getTauxRafrai());
        return listenerTempsAdmin;
    }

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


        public void changeESP(ESP esp){
            database.deleteListener();
            database.setEsp(esp);
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
            }
            curseur++;
        }
    }
}
