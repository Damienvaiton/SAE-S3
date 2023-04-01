
package com.example.saes3.ViewModel;

import androidx.annotation.Nullable;
import androidx.databinding.ObservableList;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.saes3.Model.Data;
import com.example.saes3.Model.ESP;
import com.example.saes3.Model.FirebaseAccess;
import com.example.saes3.Model.ListData;
import com.example.saes3.Util.ClassTransitoireViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SettingsAdminViewModel extends ViewModel {
    private HashMap<String, String> hashmapESP;
    private FirebaseAccess database;

    private ClassTransitoireViewModel transit;

    MutableLiveData<String> listenerTempsAdmin = new MutableLiveData<>();

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
                // TODO document why this method is empty
            }

            @Override
            public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
// TODO document why this method is empty
            }

            @Override
            public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                listenerData.postValue((Data) sender.get(positionStart));
            }

            @Override
            public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
// TODO document why this method is empty
            }

            @Override
            public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
// TODO document why this method is empty
            }
        });
    }
    public void setListenerESP() {

        database.setRealtimeDataListener();
        database.loadInData();
        database.setEspTimeListener();
    }
    private final MutableLiveData<String> liveDataRefresh = new MutableLiveData<>();

    public LiveData<Data> getListenerData(){return listenerData;}
    public void updateRefresh(String refresh){
        liveDataRefresh.postValue(refresh);
    }

    public LiveData<String> getTempsAdmin() {
        return liveDataRefresh;
    }

    public void clearTab(){
        ListData.getInstance().list_supAll_data();


    }
    ArrayList<String> tabESP = new ArrayList<>();

    public LiveData<ArrayList<String>> getHashmapESP() {
        return listener;
    }


    /**
     * Function trigger if a new ESP has been add to the databse
     * Add value to hashmap ESP and name to tabESP if he has one, macAdress if not
     */

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

    /**
     * Function trigger if a ESP has been delete to the databse
     * Remove the ESP name of tabESP if @param nameESP has been find in tabESP
     */
    public void deleteESP(String nameESP) {
        for (String s : tabESP) {
            if (s.equals(nameESP)) {
                tabESP.remove(s);
            }
            listener.postValue(tabESP);
        }
    }

    /**
     * Define the selected ESP to FirebaseAcess
     */


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